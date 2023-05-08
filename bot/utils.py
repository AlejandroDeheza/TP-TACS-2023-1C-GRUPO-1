import json
from datetime import datetime
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
        events = 'Events:\n'
        for e in events_dict['events']:
            event = format_event(e)
            events += f'{event}\n'

        return events if events else "🙅 There's no events."
    return events_dict


def format_monitoring_report(marketing_report: dict, options_report: dict):
    result = 'Monitoring report:\n'
    if type(marketing_report) is dict:
        events_count = marketing_report['events_count']
        options_count = marketing_report['options_count']
        result += f'New events created count: {events_count}\n' \
                  + f'Options voted count: {options_count}\n'

    if type(options_report) is dict:
        result += "Options voted:\n"
        options = ''
        for op in options_report['options_report']:
            date_time = op['date_time']
            vote_quantity = op['vote_quantity']
            options += f'       📅  Date time: {date_time} | Vote counts: {vote_quantity}\n'
        result += options

    return result


def get_event(event: model.Event, my_dict: dict):
    for key in my_dict:
        setattr(event, key, my_dict[key])
    return event
