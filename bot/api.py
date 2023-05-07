import requests
from telebot import types
import model
from config import SCHEME_DOMAIN


def authenticate(message: types.Message, password: str, user: model.User) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/auth/authentication'
    body = {"username": message.from_user.id, "password": password}
    response = requests.post(url, json=body)
    json = response.json()
    if response.status_code == 200:
        user.token = json['access_token']
    result = f'🎉 Login successfully with username: {message.from_user.id}' \
        if response.status_code == 200 else '😢 ' + json['message']
    return result


def logout(user: model.User) -> str:
    url = f'{SCHEME_DOMAIN}/v1/auth/logout'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.post(url, headers=headers)
    return "🎉 Logout successfully." if response.status_code == 200 else "😢  Logout failed."


def register(message: types.Message, password: str, user: model.User) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/auth/register'
    body = {"first_name": message.from_user.first_name, "last_name": message.from_user.last_name,
            "username": message.from_user.id, "password": password, "password_confirmation": password}
    response = requests.post(url, json=body)
    json = response.json()
    if response.status_code == 200:
        user.token = json['access_token']
    result = f'🎉 Sign up successfully with username: {message.from_user.id}' \
        if response.status_code == 200 else '😢 ' + json['message']
    return result


def get_all_events(user: model.User) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def get_event_by_id(user: model.User, event_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else '😢 ' + json['message']
    return result


def register_event(user: model.User, event_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}/user'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.patch(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else '😢 ' + json['message']
    return result


def vote_event_option(user: model.User, event_id: str, option_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}/options/{option_id}/vote'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.patch(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else '😢 ' + json['message']
    return result


def change_event_status(user: model.User, event_id: str, status: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}'
    params = {"status": status}
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.patch(url, params=params, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else '😢 ' + json['message']
    return result


def create_event(user: model.User, new_event) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events'
    headers = {"Authorization": f'Bearer {user.token}'}
    body = {"name": new_event.name, "description": new_event.description, "event_options": new_event.event_options}
    print(body)
    response = requests.post(url, json=body, headers=headers)
    json = response.json()
    result = json if response.status_code == 201 else '😢 ' + json['message']
    return result


def get_events_marketing_report(user: model.User) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/monitor/ratios'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else '😢 ' + json['message']
    return result


def get_options_report(user: model.User) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/monitor/options'
    headers = {"Authorization": f'Bearer {user.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    if response.status_code == 200:
        if not json:
            result = "😢 There's no options voted"
        else:
            result = json
    else:
        result = '😢 ' + json['message']
    return result
