import { type } from "os"
import { list } from "postcss"

export interface AuthenticationRequest {
    username: string
    password: string
}

export interface RegisterRequest {
    first_name: string,
    last_name: string,
    username: string,
    password: string,
    password_confirmation: string
}

export interface EventOptions {
    id: string,
    date_time: Date,
    vote_users: User[],
    vote_quantity: number
    update_time: string,
}

export interface User {
    id: string,
    first_name: string,
    last_name: string,
}

export interface Event {
    id: string,
    name: string,
    description: string,
    status: string,
    event_options: EventOptions[],
    owner_user: User,
    registered_users: User[],
    create_date: string
}

export interface Report {
    events_count: number,
    options_count: number,
}

export interface EventOptionReport {
    date_time: string,
    last_update_date: string,
    votes_quantity: number,
}