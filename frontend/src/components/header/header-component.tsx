import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { setCookie, getCookie } from 'cookies-next';
import { FaUserAlt, FaSignOutAlt } from "react-icons/fa";

function Header() {
    const username = getCookie('username')

    const handleLogout = () => {
        logout()
        setCookie('jwt', '', {maxAge: 0});
        setCookie('username', '', {maxAge: 0});
    }

    async function logout() {
        try {
            await fetch("/api/auth/logout")
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <Navbar bg="primary" variant="dark" expand="lg" sticky="top" className="mb-3">
            <Container>
                <Navbar.Brand href="/events">TACS</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav variant="pills" className="ml-auto">
                        <Nav.Link href="/events">Events</Nav.Link>
                        <Nav.Link href="/events/new-event">New Event</Nav.Link>
                        <Nav.Link href="/monitor">Monitor</Nav.Link>
                        <NavDropdown title="Perfil" id="basic-nav-dropdown">
                            <NavDropdown.ItemText><FaUserAlt className="inline mb-1"/>  {username}</NavDropdown.ItemText>
                            <NavDropdown.Divider />
                            <NavDropdown.Item onClick={handleLogout} href="/">
                            <FaSignOutAlt className="inline mb-1"/>  Sign Out
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header;