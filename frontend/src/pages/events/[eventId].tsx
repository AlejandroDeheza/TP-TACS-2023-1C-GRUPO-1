import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { Event } from "../../types/app"
import Header from "../../components/header/header-component"
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getCookie } from "cookies-next";
import moment from 'moment'

export default function Event() {
    const router = useRouter()
    const eventId = router.query.eventId
    const [event, setEvent] = useState<Event>()
    const [showVote, setShowVote] = useState(false)

    const fetchData = async () => {
        fetch(`/api/events/${eventId}`)
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    alert(reply.message)
                    return
                }
                else {
                    setEvent(reply)
                }
            })
    }

    const handleVote = (optionId: string) => {
        fetch(`/api/events/${router.query.eventId}/options/${optionId}/vote`)
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    alert(reply.message)
                    return
                }
                else {
                    fetchData()
                }
            })
    }

    const handleRegister = () => {
        fetch(`/api/events/${router.query.eventId}/user`)
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {
                    alert(reply.message)
                    return
                }
                else {
                    fetchData()
                }
            })
    }

    const getRegisteredUsersColumnsForRow = () => {
        let items = event?.registered_users.map((user => {
            return (
                <Col key={user.username}>
                    <Card bg="light" key={user.username} style={{ width: '15rem' }} className="mb-1">
                        <Card.Body>
                            <Card.Text>
                                Username: {user.username}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            );
        }));
        return items;
    };

    const getOptionsColumnsForRow = () => {
        let items = event?.event_options.map((option => {
            return (
                <Col key={option.id}>
                    <Card bg="light" key={option.id} style={{ width: '15rem' }} className="mb-1">
                        <Card.Header>Option</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                Date: {moment(option.date_time).format("YYYY/MM/DD")}                            
                            </Card.Text>
                            <Card.Text>
                                Time: {moment(option.date_time).format("kk:mm")}
                            </Card.Text>
                            <Card.Text>
                                Vote Quantity: {option.vote_quantity}
                            </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                            {showVote !== ("VOTE_CLOSED" !== event.status) && (
                                <Button variant="primary" onClick={() => handleVote(option.id)}>Vote</Button>
                            )}

                            <Button variant="primary" className="float-right" onClick={handleRegister}>Register</Button>
                        </Card.Footer>
                    </Card>
                </Col>
            );
        }));
        return items;
    };

    useEffect(() => {
        if (!getCookie('username')) {
            router.push("/")
            return
        }
        if (!eventId) {
            return
        }
        fetchData()
    }, [eventId])


    return (
        <main>
            <Header />
            <Container>
                <div>
                    <span >
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                            {event?.name}
                        </h5>
                    </span>

                    <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">Description: {event?.description}</p>
                    <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">Status: {event?.status}</p>
                </div>
                <div className="mb-3">
                    <p className="mb-2 font-normal text-gray-700 dark:text-gray-400">Registered Users: </p>
                    <Row xs={1} md={4}>
                        {getRegisteredUsersColumnsForRow()}
                    </Row>
                </div>
                <div className="mb-3">
                    <p className="mb-2 font-normal text-gray-700 dark:text-gray-400">Options: </p>
                    <Row xs={1} md={4}>
                        {getOptionsColumnsForRow()}
                    </Row>
                </div>

            </Container>
        </main>
    );
}