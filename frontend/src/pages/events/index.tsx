import { useState, useEffect } from "react";
import { Event } from "../../types/app"
import Link from "next/link"

export default function Events() {
    const [data, setData] = useState<Event[]>([])

    const fetchData = () => {
        fetch("/api/events")
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
            <ul>
                {data.map((item) => (
                    <li key={item.id}>
                        <Link href={"/events/" + item.id}>{item.name}: {item.description}</Link>
                    </li>
                ))}
            </ul>
        </main>
    );
}