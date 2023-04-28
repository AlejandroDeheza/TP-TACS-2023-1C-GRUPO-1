import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { Event } from "../../types/app"
import Header from "../../components/header/header-component"
import Footer from "../../components/footer/footer-component"

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
            <Header />
            <h1>{data?.name}</h1>
            <h2>{data?.description}</h2>
            <button onClick={() => router.back()}>Back to Events</button>
            <Footer />
        </main>
    );
}