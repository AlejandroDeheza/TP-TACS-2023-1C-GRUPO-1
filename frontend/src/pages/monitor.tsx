import { useState, useEffect } from "react";
import { Report, EventOptionReport } from "../types/app"
import Header from "../components/header/header-component"
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { getCookie } from "cookies-next";
import { useRouter } from "next/router";
import moment from 'moment'

const Monitor = () => {
    const [reportData, setReportData] = useState<Report>()
    const [optionsReportData, setOptionsReportData] = useState<EventOptionReport[]>([])
    const router = useRouter()

    const fetchReportData = () => {
        fetch("/api/monitor/ratios")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    alert(reply.message)
                }
                else {
                    setReportData(reply)
                }
            })
    }

    const fetchOptionsReportData = () => {
        fetch("/api/monitor/options")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    alert(reply.message)
                }
                else {
                    setOptionsReportData(reply)
                }
            })
    }

    useEffect(() => {
        if (!getCookie('username')) {
            router.push("/")
            return
        }
        fetchReportData()
        fetchOptionsReportData()
    }, [])

    const getColumnsForRow = () => {
        let items = optionsReportData.map((item) => {
            console.log(item.date_time);
            return (
                <Col key={item.date_time}>
                    <Card bg="light" key={item.date_time} style={{ width: '15rem' }} className="mb-3">
                        <Card.Header>Voted Option</Card.Header>
                        <Card.Body>
                            <Card.Title>
                                {item.event_name}                                
                            </Card.Title>
                            <Card.Text>
                                Date: {moment(item.date_time).format("YYYY/MM/DD")}                            
                            </Card.Text>
                            <Card.Text>
                                Time: {moment(item.date_time).format("kk:mm")}
                            </Card.Text>
                            <Card.Text>
                                Vote Quantity: {item.votes_quantity}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            );
        });
        return items;
    };


    return (
        <main>
            <Header />
            <Container>
                <Col>
                    <Card bg="light" key="event count" style={{ width: '15rem' }} className="mb-3">
                        <Card.Header>New Events Count</Card.Header>
                        <Card.Body>
                            <Card.Title>
                                In last 2 hours: {reportData?.events_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card bg="light" key="options count" style={{ width: '15rem' }} className="mb-3">
                        <Card.Header>Voted Options Count</Card.Header>
                        <Card.Body>
                            <Card.Title>
                                In last 2 hours: {reportData?.options_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Row xs={1} md={4}>
                    {getColumnsForRow()}
                </Row>
            </Container>
        </main>
    )
}

export default Monitor;