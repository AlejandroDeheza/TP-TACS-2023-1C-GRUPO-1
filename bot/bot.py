import json
import os
from datetime import datetime

import telebot
from dateutil.parser import parse
from telebot import types

import api.auth
import api.events
import api.monitor
import event
import user
from env import BOT_TOKEN

bot = telebot.TeleBot(BOT_TOKEN)


@bot.message_handler(commands=['start'])
def start(message):
    text = f'Hello {message.from_user.first_name}!\n' \
           + 'If this is your first time using the bot, please /sign_up.\n' \
           + "If not, please /login\n"
    bot.send_message(message.chat.id, text=text, parse_mode="HTML")


@bot.message_handler(commands=['help'])
def command_list(message):
    text = f'{message.from_user.first_name}! Please select a command:\n' \
           + "/all_events\n" + "/event_by_id\n" \
           + "/vote_event_option\n" + "/register_event\n" + "/change_event_status\n" + "/new_event\n" \
           + "/events_marketing_report\n" + "/options_report\n"

    bot.send_message(message.chat.id, text=text, parse_mode="HTML")


@bot.message_handler(commands=['login'])
def login_handler(message):
    text = "Please input your password."
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_authentication)


def fetch_authentication(message):
    password = message.text
    result = api.auth.authenticate(message, password)
    set_user(message)
    bot.send_message(message.chat.id, convert_result(result), parse_mode="Markdown")
    if convert_result(result) != "Login successfully.":
        bot.send_message(message.chat.id, text="Please /login again", parse_mode="HTML")
    else:
        command_list(message)


@bot.message_handler(commands=['sign_up'])
def sign_up_handler(message):
    text = "Please input your password."
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_password_confirmation)


def process_password_confirmation(message):
    password = message.text
    text = "Please repeat you password."
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_sign_up, password)


def fetch_sign_up(message, password):
    password_confirmation = message.text
    if password != password_confirmation:
        bot.send_message(message.chat.id, "Passwords are not match", parse_mode="Markdown")
    result = api.auth.register(message, password)
    set_user(message)
    bot.send_message(message.chat.id, convert_result(result), parse_mode="Markdown")
    if convert_result(result) != "Sign up successfully.":
        bot.send_message(message.chat.id, text="Please /sign_up again", parse_mode="HTML")
    else:
        command_list(message)


@bot.message_handler(commands=['all_events'])
def all_events_handler(message):
    if user.User.token:
        result = convert_result(api.events.get_all_events())
        bot.send_message(message.chat.id, result, parse_mode="HTML")
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")
    command_list(message)


@bot.message_handler(commands=['event_by_id'])
def event_by_id_handler(message):
    if user.User.token:
        text = "Please input the event id."
        sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
        bot.register_next_step_handler(sent_msg, fetch_event)
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")


def fetch_event(message):
    event_id = message.text
    result = api.events.get_event_by_id(event_id)
    bot.send_message(message.chat.id, convert_result(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['register_event'])
def register_event_handler(message):
    if user.User.token:
        text = "Please input the event id."
        sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
        bot.register_next_step_handler(sent_msg, fetch_register_event)
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")


def fetch_register_event(message):
    event_id = message.text
    result = api.events.register_event(event_id)
    bot.send_message(message.chat.id, convert_result(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['change_event_status'])
def change_event_status_handler(message):
    if user.User.token:
        text = "Please input the event id."
        sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
        bot.register_next_step_handler(sent_msg, process_status)
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")


def process_status(message):
    event_id = message.text
    text = "Please select the status."
    vote_closed = {"status": "VOTE_CLOSED", "id": event_id}
    vote_pending = {"status": "VOTE_PENDING", "id": event_id}
    button_yes = types.InlineKeyboardButton('VOTE CLOSED', callback_data=json.dumps(vote_closed))
    button_no = types.InlineKeyboardButton('VOTE PENDING', callback_data=json.dumps(vote_pending))
    keyboard = types.InlineKeyboardMarkup()
    keyboard.add(button_yes)
    keyboard.add(button_no)
    bot.send_message(message.chat.id, text, reply_markup=keyboard)


@bot.callback_query_handler(lambda call: json.loads(call.data)['status'] in ["VOTE_CLOSED", "VOTE_PENDING"])
def fetch_change_event_status(call):
    status = json.loads(call.data)['status']
    result = api.events.change_event_status(json.loads(call.data)['id'], status)
    bot.send_message(call.message.chat.id, convert_result(result), parse_mode="HTML")
    command_list(call.message)


@bot.message_handler(commands=['vote_event_option'])
def vote_event_option_handler(message):
    if user.User.token:
        text = "Please input the event id."
        sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
        bot.register_next_step_handler(sent_msg, process_option_id)
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")


def process_option_id(message):
    event_id = message.text
    text = "Please input the option id"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_vote_event_option, event_id)


def fetch_vote_event_option(message, event_id):
    option_id = message.text
    result = api.events.vote_event_option(event_id, option_id)
    bot.send_message(message.chat.id, convert_result(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['events_marketing_report'])
def events_marketing_report(message):
    if user.User.token:
        result = api.monitor.get_events_marketing_report()
        bot.send_message(message.chat.id, convert_result(result), parse_mode="HTML")
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")
    command_list(message)


@bot.message_handler(commands=['options_report'])
def options_report(message):
    if user.User.token:
        result = api.monitor.get_options_report()
        bot.send_message(message.chat.id, convert_result(result), parse_mode="HTML")
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")
    command_list(message)


@bot.message_handler(commands=['new_event'])
def new_event_handler(message):
    if user.User.token:
        event.Event.event_options = list()
        text = "Please input the event name."
        sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
        bot.register_next_step_handler(sent_msg, process_event_description)
    else:
        bot.send_message(message.chat.id, "Please login first.", parse_mode="Markdown")


def process_event_description(message):
    event.Event.name = message.text
    text = "Please input the event description"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_first_option)


def process_first_option(message):
    if not event.Event.description:
        event.Event.description = message.text
    text = "Please input the date time of the option. Example: 2023-05-06T23:58."
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_next_option)


def process_next_option(message):
    date_time = message.text
    if is_valid_date(date_time):
        event.Event.event_options.append({"date_time": date_time})
        text = "You need to add more event option?"
        button_yes = types.InlineKeyboardButton('Yes', callback_data='Y')
        button_no = types.InlineKeyboardButton('No', callback_data='N')
        keyboard = types.InlineKeyboardMarkup()
        keyboard.add(button_yes)
        keyboard.add(button_no)
        bot.send_message(message.chat.id, text, reply_markup=keyboard)
    else:
        bot.send_message(message.chat.id, "Date time invalid.", parse_mode="Markdown")
        process_first_option(message)


@bot.callback_query_handler(func=lambda call: call.data == 'Y')
def process_more_event_option(call):
    text = "Please input the date time of the option"
    sent_msg = bot.send_message(call.message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_next_option)


@bot.callback_query_handler(func=lambda call: call.data == 'N')
def fetch_create_event(call):
    result = api.events.create_event(event.Event)
    bot.send_message(call.message.chat.id, convert_result(result), parse_mode="HTML")
    command_list(call.message)


########################################################################################
def set_user(message):
    user.User.username = f'{message.from_user.id}'
    user.User.first_name = message.from_user.first_name
    user.User.last_name = message.from_user.last_name


def convert_result(result):
    if type(result) is dict:
        return json.dumps(result, indent=1)
    return result


def is_valid_date(date):
    if date:
        try:
            parse(date)
            return True
        except:
            return False
    return False


bot.infinity_polling()
