import { useState, useEffect } from "react";
import { Report, EventOptionReport } from "../types/app"
import Header from "../components/header/header-component"
import Footer from "../components/footer/footer-component"
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

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
            <Container>
                <Col>
                    <Card bg="light" key="event count" style={{ width: '18rem' }} className="mb-2">
                        <Card.Body>
                            <Card.Title>
                                Events Count in last 2 hours: {reportData?.events_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card bg="light" key="options count" style={{ width: '18rem' }} className="mb-2">
                        <Card.Body>
                            <Card.Title>
                                Options Count in last 2 hours: {reportData?.options_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Row xs={1} md={4}>
                    <Col>
                        {optionsReportData.map((item) => (
                            <Card bg="light" key={item.date_time} style={{ width: '18rem' }} className="mb-1">
                                <Card.Body>
                                    <Card.Title>
                                        Option
                                    </Card.Title>
                                    <Card.Text>
                                        Date Time: {item.date_time}
                                    </Card.Text>
                                    <Card.Text>
                                        Vote Quantity: {item.votes_quantity}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        ))}
                    </Col>
                </Row>
            </Container>
        </main>
    )
}

export default Monitor;