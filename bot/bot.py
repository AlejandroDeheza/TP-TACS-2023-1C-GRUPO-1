import json
import telebot
from telebot import types
import api
import model
import utils
from config import BOT_TOKEN

bot = telebot.TeleBot(BOT_TOKEN)
users = dict()


@bot.message_handler(commands=['start'])
def start(message):
    print(f'User: {message.from_user.id} /start')
    text = f'😊 Hello {message.from_user.first_name}!\n' \
           + 'If this is your first time using the bot, please /sign_up\n' \
           + "If not, please /login\n"
    bot.send_message(message.chat.id, text=text, parse_mode="HTML")


def command_list(message):
    text = f'{message.from_user.first_name}!\n' + 'Please select a command:\n' \
           + "/all_events\n" + "/event_by_id\n" \
           + "/vote_event_option\n" + "/register_event\n" + "/change_event_status\n" + "/new_event\n" \
           + "/events_marketing_report\n" + "/options_report\n"
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            text += "Or 🛑 /logout\n"
        bot.send_message(message.chat.id, text=text, parse_mode="HTML")
    except KeyError:
        bot.send_message(message.chat.id, text, parse_mode="HTML")


@bot.message_handler(commands=['login'])
def login_handler(message):
    print(f'User: {message.from_user.id} /login')
    text = "😶 Please input your password:"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_login)


@bot.message_handler(commands=['logout'])
def logout_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            result = api.logout(user)
            if "Logout successfully" in result:
                users.pop(user.username)
                bot.send_message(message.chat.id, result, parse_mode="HTML")
                start(message)
            else:
                result += ' Please /logout again'
                bot.send_message(message.chat.id, result, parse_mode="HTML")
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def fetch_login(message):
    password = message.text
    bot.delete_message(message.chat.id, message.id)
    user = get_user(message)
    result = utils.convert_result(api.authenticate(message, password, user))
    if "Login successfully" not in result:
        bot.send_message(message.chat.id, text=result + ". Please /login again", parse_mode="HTML")
    else:
        users[user.username] = user
        bot.send_message(message.chat.id, text=result, parse_mode="HTML")
        command_list(message)


@bot.message_handler(commands=['sign_up'])
def sign_up_handler(message):
    print(f'User: {message.from_user.id} /sign_up')
    text = "😶 Please input your password:"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_password_confirmation)


def process_password_confirmation(message):
    password = message.text
    bot.delete_message(message.chat.id, message.id)
    text = "😶 Please repeat you password:"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_sign_up, password)


def fetch_sign_up(message, password):
    password_confirmation = message.text
    bot.delete_message(message.chat.id, message.id)
    if password != password_confirmation:
        bot.send_message(message.chat.id, "😢 Passwords are not match. Please /sign_up again", parse_mode="HTML")
    else:
        user = get_user(message)
        result = utils.convert_result(api.register(message, password, user))
        if "already exists" in result:
            bot.send_message(message.chat.id, text=result + ". Please /login", parse_mode="HTML")
        else:
            if "Sign up successfully" in result:
                users[user.username] = user
                bot.send_message(message.chat.id, text=result, parse_mode="HTML")
                command_list(message)
            else:
                bot.send_message(message.chat.id, text=result + " Please /sign_up again", parse_mode="HTML")


@bot.message_handler(commands=['all_events'])
def all_events_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            result = utils.format_events(api.get_all_events(user))
            bot.send_message(message.chat.id, result, parse_mode="HTML")
            command_list(message)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


@bot.message_handler(commands=['event_by_id'])
def event_by_id_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            text = "🆔 Please input the event id:"
            sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
            bot.register_next_step_handler(sent_msg, fetch_event, user)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def fetch_event(message, user):
    event_id = message.text
    result = api.get_event_by_id(user, event_id)
    bot.send_message(message.chat.id, utils.format_event(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['register_event'])
def register_event_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            text = "🆔 Please input the event id:"
            sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
            bot.register_next_step_handler(sent_msg, fetch_register_event, user)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def fetch_register_event(message, user):
    event_id = message.text
    result = api.register_event(user, event_id)
    bot.send_message(message.chat.id, utils.format_event(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['change_event_status'])
def change_event_status_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            text = "Please input the event id:"
            sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
            bot.register_next_step_handler(sent_msg, process_status)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def process_status(message):
    event_id = message.text
    text = "Please select the status."
    vote_closed = {"status": "VOTE_CLOSED", "id": event_id}
    vote_pending = {"status": "VOTE_PENDING", "id": event_id}
    button_yes = types.InlineKeyboardButton('❌ VOTE CLOSED', callback_data=json.dumps(vote_closed))
    button_no = types.InlineKeyboardButton('⭕ VOTE PENDING', callback_data=json.dumps(vote_pending))
    keyboard = types.InlineKeyboardMarkup()
    keyboard.add(button_yes)
    keyboard.add(button_no)
    bot.send_message(message.chat.id, text, reply_markup=keyboard)


@bot.callback_query_handler(lambda call: 'status' in call.data)
def fetch_change_event_status(call):
    username = f'{call.from_user.id}'
    user = users[username]
    status = json.loads(call.data)['status']
    result = api.change_event_status(user, json.loads(call.data)['id'], status)
    bot.send_message(call.message.chat.id, utils.format_event(result), parse_mode="HTML")
    command_list(call.message)


@bot.message_handler(commands=['vote_event_option'])
def vote_event_option_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            text = "🆔 Please input the event id:"
            sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
            bot.register_next_step_handler(sent_msg, process_option_id)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def process_option_id(message):
    event_id = message.text
    text = "🆔 Please input the option id:"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, fetch_vote_event_option, event_id)


def fetch_vote_event_option(message, event_id):
    username = f'{message.from_user.id}'
    user = users[username]
    option_id = message.text
    result = api.vote_event_option(user, event_id, option_id)
    bot.send_message(message.chat.id, utils.format_event(result), parse_mode="HTML")
    command_list(message)


@bot.message_handler(commands=['events_marketing_report'])
def events_marketing_report(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            result = api.get_events_marketing_report(user)
            bot.send_message(message.chat.id, utils.format_events_marketing_report(result), parse_mode="HTML")
            command_list(message)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


@bot.message_handler(commands=['options_report'])
def options_report(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            result = api.get_options_report(user)
            bot.send_message(message.chat.id, utils.format_options_report(result), parse_mode="HTML")
            command_list(message)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


@bot.message_handler(commands=['new_event'])
def new_event_handler(message):
    username = f'{message.from_user.id}'
    try:
        user = users[username]
        if user.token:
            model.Event.event_options = list()
            text = "📝 Please input the event name:"
            sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
            bot.register_next_step_handler(sent_msg, process_event_name)
        else:
            bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")
    except KeyError:
        bot.send_message(message.chat.id, "😢 Please /login first", parse_mode="Markdown")


def process_event_name(message):
    event = model.Event()
    event.name = message.text
    text = "📄 Please input the event description:"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, process_event_description, event)


def process_event_description(message, event):
    event.description = message.text
    ask_date_time(message, event)


def ask_date_time(message, event):
    text = "📅 Please input the date time of the option (Example: 2023-05-06T23:58):"
    sent_msg = bot.send_message(message.chat.id, text, parse_mode="Markdown")
    bot.register_next_step_handler(sent_msg, option_handler, event)


def option_handler(message, event):
    date_time = message.text
    if utils.is_valid_date(date_time):
        event.event_options.append({"date_time": date_time})
        ask_more_option(message, event)
    else:
        bot.send_message(message.chat.id, "😢 Date time invalid.", parse_mode="Markdown")
        ask_date_time(message, event)


def ask_more_option(message, event):
    reply = types.ForceReply()
    text = "You need to add more event option ❓" + '✅ Y' + '❌ N'
    sent_msg = bot.send_message(message.chat.id, text, reply_markup=reply)
    bot.register_next_step_handler(sent_msg, process_option, event)


def process_option(message, event):
    if 'Y' in message.text or 'y' in message.text:
        ask_date_time(message, event)
    elif 'N' in message.text or 'n' in message.text:
        fetch_create_event(message, event)
    else:
        bot.send_message(message.chat.id, "😢 Answer invalid.", parse_mode="Markdown")
        ask_more_option(message, event)


def fetch_create_event(message, event):
    print(event.name)
    username = f'{message.from_user.id}'
    user = users[username]
    result = api.create_event(user, event)
    bot.send_message(message.chat.id, utils.format_event(result), parse_mode="HTML")
    command_list(message)


def get_user(message):
    username = f'{message.from_user.id}'
    return utils.get_user(model.User(), {"username": username, "first_name": message.from_user.first_name,
                                         "last_name": message.from_user.last_name})


def main():
    print("Bot starting...")
    bot.infinity_polling()
    print("Bot stopped")


if __name__ == "__main__":
    main()
