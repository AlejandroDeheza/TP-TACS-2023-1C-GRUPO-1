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

export default function Events() {
    const router = useRouter()
    const [data, setData] = useState<Event[]>([])
    const [showClose, setShowClose] = useState(false)
    const username = getCookie('username')

    const fetchData = () => {
        fetch("/api/events")
            .then((response) => {
                return response.json()
            })
            .then((reply) => {
                if (reply.message) {                    
                    alert(reply.message)
                }
                else {
                    setData(reply)
                }
            })
    }

    const handleClose = (eventId: string) => {
        fetch(`/api/events/${eventId}?status=VOTE_CLOSED`, {
            method: "PATCH",
        }).then((response) => {
            console.log(response)
            return response.json()
        }).then((reply) => {
            fetchData()
        })
    }

    const getColumnsForRow = () => {
        let items = data.map((event => {
            return (
                <Col key={event.id}>
                    <Card bg="light" key={event.id} style={{ width: '15rem' }} className="mb-3">
                        <Card.Header>{event.name}</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                Status: {event.status}
                            </Card.Text>
                            <Card.Text>
                                Owner User: {event?.owner_user.username}
                            </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                            <Link href={"/events/" + event.id}>
                                <Button variant="primary">Details</Button>
                            </Link>
                            {showClose !== ((username == event.owner_user.username) && ("VOTE_CLOSED" !== event.status)) && (
                                <Button variant="danger" className="float-right" onClick={() => handleClose(event.id)}>Close Vote</Button>
                            )}
                        </Card.Footer>
                    </Card>
                </Col>
            );
        }));
        return items;
    };

    useEffect(() => {
        if(!username) {
            router.push("/")
            return
        }
        fetchData()
    }, [])

    return (
        <main >
            <Header />
            <Container>
                <Row xs={1} md={4}>
                    {getColumnsForRow()}
                </Row>
            </Container>
        </main>
    );
}