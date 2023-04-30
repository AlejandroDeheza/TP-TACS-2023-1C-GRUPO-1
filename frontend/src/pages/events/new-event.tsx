import Header from "../../components/header/header-component"
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { getCookie } from "cookies-next";
import Form from 'react-bootstrap/Form';
import { EventRequest, EventOptionRequest } from "../../types/app"
import { FormEvent, useState } from "react";
import { useRouter } from "next/router"

export default function NewEvent() {
    const router = useRouter()
    const [myEvent, setMyEvent] = useState<EventRequest>({
        name: "",
        description: "",
        event_options:[]
    })
    const [fields, setFields] = useState([{
        id: 1,
        date_time: "",
    }])

    const handleChangeEvent = (event: any) => {
        setMyEvent({ ...myEvent, [event.target.name]: event.target.value });
      };

    const handleChangeInput = (i: number,e: any) => {
        const values = [...fields]
        values[i][e.target.name] = e.target.value
        setFields(values)
    }


    const handleAdd = (id: number) => {
        setFields([...fields, { id: id + 2, date_time: '' }])
    }

    const handleSubtract = (i: number) => {
        const values = [...fields]
        values.splice(i, 1)
        setFields([...values])
    }

    async function createNewEvent() {
        try {
          const response = await fetch("/api/events", {
            method: "POST",
            body: JSON.stringify(myEvent),
            headers: {
                'Content-Type': 'application/json',
            },
          });
          const userData = await response.json()
          if(response.status == 201){
            router.push("/events")
          }
          else{
            alert(userData.message)
          }
    
        } catch (error) {
          console.log(error);
        }
      };

      const handleSubmit = async (e: FormEvent) => {
        e.preventDefault()

        for (let i=0; i<fields.length; i++) {
            myEvent.event_options.push({date_time:fields[i].date_time} as EventOptionRequest)
         }
        await createNewEvent()
      }

    return (
        <main>
            <Header />
            <Container>
                <Row>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Row className="mt-3">
                                <Col md>
                                    <Form.Label>Event Name</Form.Label>
                                    <Form.Control type="text" name="name" placeholder="Event Name" onChange={handleChangeEvent}/>
                                </Col>

                                <Col md>
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control type="text" name="description" placeholder="Description" onChange={handleChangeEvent}/>
                                </Col>
                            </Row>
                            {fields.map((field, i) => (
                                <Row key={i}>
                                    <Col className="mt-3">
                                        <Form.Label>Date Time</Form.Label>
                                        <Form.Control type="datetime-local" name="date_time" placeholder="Date" onChange={e => handleChangeInput(i, e)}/>
                                    </Col>
                                    <Col className="mt-5">
                                        <Button variant="primary" className="mr-3" onClick={() => handleAdd(i)}>
                                            + Option
                                        </Button>
                                        <Button variant="primary" className="mr-3" onClick={() => handleSubtract(i)} disabled={field.id === 1}>
                                            - Option
                                        </Button>
                                    </Col>
                                </Row>
                            ))}

                        </Form.Group>

                        <Button variant="primary" type="submit" >
                            Submit
                        </Button>
                    </Form>
                </Row>
            </Container >
        </main >
    );
}

function setInputFields(arg0: any[]) {
    throw new Error("Function not implemented.");
}
