import { useState, useEffect } from "react";
import { Event } from "../../types/app"
import Link from "next/link"
import Header from "../../components/header/header-component"
import Footer from "../../components/footer/footer-component"

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
            <Header />
            <ul>
                {data.map((item) => (
                    <li key={item.id}>
                        <div className="max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow 
                        dark:bg-gray-800 dark:border-gray-700">
                            <Link href={"/events/" + item.id}>
                                <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                                    {item.name}
                                </h5>
                            </Link>

                            <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">Description: {item.description}</p>
                            <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">Status: {item.status}</p>

                            <Link href={"/events/" + item.id} className="inline-flex items-center px-3 py-2 text-sm 
                            font-medium text-center text-white bg-blue-500 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                Read more
                            </Link>
                        </div>
                    </li>
                ))}
            </ul>
            <Footer />
        </main>
    );
}