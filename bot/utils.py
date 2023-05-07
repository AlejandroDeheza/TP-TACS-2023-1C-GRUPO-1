import json
from datetime import datetime

from dateutil.parser import parse

import model


def convert_result(result):
    if type(result) is dict:
        return json.dumps(result, indent=1)
    return result


def is_valid_date(date):
    if date:
        try:
            datetime.fromisoformat(date)
            return True
        except ValueError:
            return False
    return False


def format_event(event_dict: dict):
    if type(event_dict) is dict and event_dict:
        print(event_dict)
        event = get_event(model.Event(), event_dict)
        username = event.owner_user['username'] if event.owner_user else ''

        registered_users = ''
        for u in event.registered_users:
            name = u['username']
            registered_users += f'      😁 {name}\n'

        options = ''
        for op in event.event_options:
            op_id = op['id']
            date_time = op['date_time']
            vote_quantity = op['vote_quantity']
            options += f'      📅 Option id: {op_id} | Date time: {date_time} | Vote counts: {vote_quantity}\n'

        return f'🆔 Event id: {event.id}\n' \
            + f'Event name: {event.name}\n' \
            + f'Event description: {event.description}\n' \
            + f'Event status: {event.status}\n' \
            + f'Owner user: 😎 {username}\n' \
            + 'Registered users:\n' \
            + registered_users \
            + 'Event options:\n' \
            + options
    return event_dict


def format_events(events_dict: dict):
    if type(events_dict) is dict:
        print(events_dict)
        events = ''
        for e in events_dict['events']:
            event = format_event(e)
            events += f'{event}\n'

        return events if events else "🙅 There's no events."
    return events_dict


def format_events_marketing_report(marketing_report: dict):
    if type(marketing_report) is dict:
        events_count = marketing_report['events_count']
        options_count = marketing_report['options_count']
        return f'New events created count: {events_count}\n' \
            + f'Options voted count: {options_count}\n'
    return marketing_report


def format_options_report(options_dict: dict):
    if type(options_dict) is dict:
        print(options_dict)
        options = ''
        for op in options_dict['options_report']:
            date_time = op['date_time']
            vote_quantity = op['vote_quantity']
            options += f'📅  Option date time: {date_time} | Vote counts: {vote_quantity}\n'
        return options if options else "🙅 There's no options voted."
    return options_dict


def get_event(event: model.Event, my_dict: dict):
    for key in my_dict:
        setattr(event, key, my_dict[key])
    return event


def get_user(user: model.User, my_dict: dict):
    for key in my_dict:
        setattr(user, key, my_dict[key])
    return user


def get_event_option(event_option: model.EventOption, my_dict: dict):
    for key in my_dict:
        setattr(event_option, key, my_dict[key])
    return event_option
