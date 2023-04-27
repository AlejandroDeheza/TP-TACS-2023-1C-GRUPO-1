import { type } from "os"
import { list } from "postcss"

type AuthenticationRequest = {
    username: string,
    password: string,
}

type EventRequest = {
    name: string,
    description: string,
    event_options: EventOptions[],
}

type EventOptions = {
    date_time: Date,
}
