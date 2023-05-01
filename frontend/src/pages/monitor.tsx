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
import { FaCalculator, FaCalendarCheck } from "react-icons/fa";
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const Monitor = () => {
    const [reportData, setReportData] = useState<Report>()
    const [optionsReportData, setOptionsReportData] = useState<EventOptionReport[]>([])
    const router = useRouter()
    const [showAlert, setShowAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState(false)

    const fetchReportData = () => {
        fetch("/api/monitor/ratios")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    setAlertMessage(reply.message)
                    setShowAlert(true)
                    return
                }
                else {
                    setReportData(reply)
                }
            })
        fetch("/api/monitor/options")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    setAlertMessage(reply.message)
                    setShowAlert(true)
                    return
                }
                else {
                    setOptionsReportData(reply)
                }
            })
    }

    const handleCloseAlert = () => {
        setShowAlert(false)
        fetchReportData()
    }


    useEffect(() => {
        if (!getCookie('username')) {
            router.push("/")
            return
        }
        fetchReportData()
    }, [])

    const getColumnsForRow = () => {
        let items = optionsReportData.map((item) => {
            console.log(item.date_time);
            return (
                <Col md={"auto"} key={item.date_time}>
                    <Card bg="light" key={item.date_time} style={{ width: '13rem' }} className="mb-4">
                        <Card.Header><FaCalendarCheck className="inline mb-1" />  Voted Option</Card.Header>
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
                    <Card bg="light" key="event count" style={{ width: '13rem' }} className="mb-4">
                        <Card.Header><FaCalculator className="inline mb-1" />  New Events Count</Card.Header>
                        <Card.Body>
                            <Card.Title>
                                In last 2 hours: {reportData?.events_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Col>
                    <Card bg="light" key="options count" style={{ width: '13rem' }} className="mb-4">
                        <Card.Header><FaCalculator className="inline mb-1" />  Voted Options Count</Card.Header>
                        <Card.Body>
                            <Card.Title>
                                In last 2 hours: {reportData?.options_count}
                            </Card.Title>
                        </Card.Body>
                    </Card>
                </Col>
                <Row md={"auto"}>
                    {getColumnsForRow()}
                </Row>
            </Container>
            <Modal show={showAlert}>
                <Modal.Header closeButton>
                    <Modal.Title>Error!</Modal.Title>
                </Modal.Header>
                <Modal.Body>{alertMessage}</Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCloseAlert}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </main>
    )
}

export default Monitor;