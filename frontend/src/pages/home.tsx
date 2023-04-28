import Link from "next/link"
import Header from "../components/header/header-component"
import Footer from "../components/footer/footer-component"

export default function Home() {
  return (
    <main>
      <Header />
      <h1>
        <Link href={"/monitor"}>Monitor</Link>
      </h1>
      <h1>
        <Link href={"/events"}>Events</Link>
      </h1>
      <Footer />
    </main>
  );
}
