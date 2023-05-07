from typing import List


class EventOption:
    id: str
    date_time: str
    vote_quantity: str

    def __init__(self):
        self.id = ''
        self.date_time = ''
        self.vote_quantity = ''


class User:
    username: str
    first_name: str
    last_name: str
    token: str

    def __init__(self):
        self.username = ''
        self.first_name = ''
        self.last_name = ''
        self.token = ''


class Event:
    id: str
    name: str
    description: str
    status: str
    owner_user: User
    event_options: List[EventOption]
    registered_users: List[User]

    def __init__(self):
        self.id = ''
        self.name = ''
        self.description = ''
        self.status = ''
        self.event_options = list[EventOption]()
        self.registered_users = list[User]()

