import os

import requests

import user
from env import SCHEME_DOMAIN


def get_all_events() -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def get_event_by_id(event_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def register_event(event_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}/user'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.patch(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def vote_event_option(event_id: str, option_id: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}/options/{option_id}/vote'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.patch(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def change_event_status(event_id: str, status: str) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events/{event_id}'
    params = {"status": status}
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.patch(url, params=params, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def create_event(new_event) -> dict:
    url = f'{SCHEME_DOMAIN}/v1/events'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    body = {"name": new_event.name, "description": new_event.description, "event_options": new_event.event_options}
    print(body)
    response = requests.post(url, json=body, headers=headers)
    json = response.json()
    result = json if response.status_code == 201 else json['message']
    return result
