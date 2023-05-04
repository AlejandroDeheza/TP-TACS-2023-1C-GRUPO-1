import { useState, useEffect } from "react";
import { Event } from "../../types/app"
import Link from "next/link"
import Header from "../../components/header/header-component"
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { getCookie } from "cookies-next";
import { useRouter } from "next/router";
import Modal from 'react-bootstrap/Modal';
import { FaCalendarAlt, FaListAlt, FaWindowClose, FaCheckCircle} from "react-icons/fa";


export default function Events() {
    const router = useRouter()
    const [data, setData] = useState<Event[]>([])
    const [showOpen, setShowOpen] = useState(false)
    const [showClose, setShowClose] = useState(false)
    const [showAlert, setShowAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState(false)
    const username = getCookie('username')

    const fetchData = () => {
        fetch("/api/events")
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
                    setData(reply)
                }
            })
    }

    const handleCloseAlert = () => {
        setShowAlert(false)
        fetchData()
    }

    const mapEventStatus = (status: string) => {
        if(status === "VOTE_CLOSED"){
            return "VOTE CLOSED"
        }
        if(status === "VOTE_PENDING"){
            return "VOTE PENDING"
        }
    }

    const handleChangeEventStatus = (eventId: string, status: string) => {
        fetch(`/api/events/${eventId}?status=${status}`, {
            method: "PATCH",
        }).then((response) => {
            console.log(response)
            return response.json()
        }).then((reply) => {
            if (reply.message) {
                setAlertMessage(reply.message)
                setShowAlert(true)
                return
            }
            else {
                fetchData()
            }
        })
    }

    const getColumnsForRow = () => {
        let items = data.map((event => {
            return (
                <Col md={"auto"} key={event.id}>
                    <Card bg="light" key={event.id} style={{ width: '18rem' }} className="mb-4">
                        <Card.Header><FaCalendarAlt className="inline mb-1"/>  {event.name}</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                Status: {mapEventStatus(event.status)}
                            </Card.Text>
                            <Card.Text>
                                Owner User: {event?.owner_user.username}
                            </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                            <Link href={"/events/" + event.id}>
                                <Button variant="primary"><FaListAlt className="inline mb-1"/> Details</Button>
                            </Link>
                            {showClose !== ((username == event.owner_user.username) && ("VOTE_CLOSED" !== event.status)) && (
                                <Button variant="danger" className="float-right" onClick={() => handleChangeEventStatus(event.id, "VOTE_CLOSED")}><FaWindowClose className="inline mb-1"/>  Close Vote</Button>
                            )}
                            {showOpen !== ((username == event.owner_user.username) && ("VOTE_PENDING" !== event.status)) && (
                                <Button variant="primary" className="float-right" onClick={() => handleChangeEventStatus(event.id, "VOTE_PENDING")}><FaCheckCircle className="inline mb-1"/>  Open Vote</Button>
                            )}
                        </Card.Footer>
                    </Card>
                </Col>
            );
        }));
        return items;
    };

    useEffect(() => {
        if (!username) {
            router.push("/")
            return
        }
        fetchData()
    }, [])

    return (
        <main >
            <Header />
            <Container fluid="lg">
                <Row md={"auto"} >
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
        </main >
    );
}
