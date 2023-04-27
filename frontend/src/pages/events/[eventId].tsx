import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { Event } from "../../types/app"

export default function Event() {
    const router = useRouter()
    const [data, setData] = useState<Event>()

    const fetchData = () => {
        fetch(`/api/events/${router.query.eventId}`)
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                console.log(reply)
                setData(reply)
            })
    }

    useEffect(() => {
        fetchData()
    }, [])

    return (
        <main>
            <h1>{data?.name}</h1>
            <button onClick={() => router.back()}>Back to Events</button>
        </main>
    );
}