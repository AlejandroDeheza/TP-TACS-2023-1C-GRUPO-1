import Link from "next/link";
import { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { getCookie } from 'cookies-next';
import { NextApiRequest, NextApiResponse } from "next";

function Header() {
    const username = getCookie('username')

    return (
        <Navbar bg="primary" variant="dark" expand="lg" sticky="top" className="mb-3">
            <Container>
                <Navbar.Brand href="#home">TACS</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                    <Nav.Link href="/events/new-event">New Event</Nav.Link>
                        <Nav.Link href="/events">Events</Nav.Link>
                        <Nav.Link href="/monitor">Monitor</Nav.Link>
                        <NavDropdown title="Perfil" id="basic-nav-dropdown">
                            <NavDropdown.ItemText>{username}</NavDropdown.ItemText>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">
                                Sign Out
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>


    )
}

export default Header;