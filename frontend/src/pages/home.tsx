import Link from "next/link"


export default function Home() {
  return (
    <main>
      <h1>
        <Link href={"/monitor"}>Monitor</Link>
      </h1>
      <h1>
        <Link href={"/events"}>Events</Link>
      </h1>
    </main>
  );
}
