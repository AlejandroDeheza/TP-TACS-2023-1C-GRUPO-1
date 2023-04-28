import { useState, useEffect } from "react";
import { Report, EventOptionReport } from "../types/app"
import Header from "../components/header/header-component"
import Footer from "../components/footer/footer-component"

const Monitor = () => {
    const [reportData, setReportData] = useState<Report>()
    const [optionsReportData, setOptionsReportData] = useState<EventOptionReport[]>([])

    const fetchReportData = () => {
        fetch("/api/monitor/ratios")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                console.log(reply)
                setReportData(reply)
            })
    }

    const fetchOptionsReportData = () => {
        fetch("/api/monitor/options")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                console.log(reply)
                setOptionsReportData(reply)
            })
    }

    useEffect(() => {
        fetchReportData()
        fetchOptionsReportData()
    }, [])

    return (
        <main>
            <Header />
            <h1>Events count: {reportData?.events_count}</h1>
            <h1>Options count: {reportData?.options_count}</h1>
            <ul>
                {optionsReportData.map((item) => (
                    <li key={item.date_time}>
                        <h4>{item.last_update_date}: {item.votes_quantity}</h4>
                    </li>
                ))}
            </ul>
            <Footer />
        </main>
    )
}

export default Monitor;