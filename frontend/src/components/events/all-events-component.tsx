import React from 'react';

export default function AllEventsComponent(ctx: any) {
  async function getAllEvents() {
    const response = await fetch('/api/events', {
      method: 'GET',
    });

    const data = await response.json()
    console.log(data)
    if (!response.ok) {
      throw new Error('Something went wrong!');
    }
  };

  getAllEvents();

  return (
    <h1>All Events</h1>
  );
};