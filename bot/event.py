class EventOption:
    date_time: str = ''


class Event:
    name: str = ''
    description: str = ''
    event_options: [EventOption] = list()
