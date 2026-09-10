const title = document.getElementById("welcome-title");
title.textContent = "Welcome To Rehana Groups Hotel";


const form = document.getElementById("search-form");
const errorMessage = document.getElementById("search-error");


form.addEventListener("submit", (event) => {
    event.preventDefault();

    const checkIn = form.elements["checkIn"].value;
    const checkOut = form.elements["checkOut"].value;
    const guests = form.elements["guests"].value;

    if (guests <= 0) {
        errorMessage.textContent = "Guests must be greater than 0";
        errorMessage.classList.add("error-message");
        return;
    }

    const today = new Date().toISOString().split("T")[0];

    if (checkIn < today) {
        errorMessage.textContent = "Check-in date cannot be in the past";
        errorMessage.classList.add("error-message");
        return;
    }

    if (checkOut <= checkIn) {
        errorMessage.textContent = "Check-out must be after check-in";
        errorMessage.classList.add("error-message");
        return;
    }

    errorMessage.textContent = "";
    errorMessage.classList.remove("error-message");

    form.submit();
});


async function loadRooms() {

    try {
        const response = await fetch("/api/rooms");

        if (!response.ok) {
            throw new Error("Failed to load rooms");
        }

        const rooms = await response.json();

        const roomsContainer = document.getElementById("rooms-container");

        rooms.forEach(room => {

            const card = document.createElement("div");

            card.className =
                "group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm " +
                "transition duration-300 hover:-translate-y-1 hover:border-amber-300 hover:shadow-xl";

            card.innerHTML = `
    <div class="mb-5 flex items-start justify-between">
        <div>
            <p class="text-xs font-semibold uppercase tracking-widest text-amber-600">
                ${room.roomType}
            </p>

            <h3 class="mt-1 text-2xl font-bold text-slate-900">
                Room ${room.roomNumber}
            </h3>
        </div>

        <div class="rounded-lg bg-slate-100 px-3 py-2 text-right">
            <p class="text-lg font-bold text-slate-900">
                $${room.price}
            </p>
            <p class="text-xs text-slate-500">
                per night
            </p>
        </div>
    </div>

    <div class="mb-5 space-y-2 border-y border-slate-100 py-4">
        <p class="text-sm text-slate-600">
            <span class="font-semibold text-slate-800">Capacity:</span>
            ${room.capacity} guests
        </p>

        <p class="text-sm leading-6 text-slate-500">
            ${room.description}
        </p>
    </div>

    ${
                room.active
                    ? `
                <div class="flex gap-3">
                    <a href="/rooms/${room.roomId}"
                       class="flex-1 rounded-lg border border-slate-300 px-4 py-2.5
                              text-center text-sm font-semibold text-slate-700
                              transition hover:border-slate-900 hover:bg-slate-50">
                        View Details
                    </a>

                    <a href="/rooms/${room.roomId}/book"
                       class="flex-1 rounded-lg bg-slate-900 px-4 py-2.5
                              text-center text-sm font-semibold text-white
                              transition hover:bg-slate-800">
                        Book Now
                    </a>
                </div>
            `
                    : `
                <div class="rounded-lg bg-slate-100 px-4 py-3 text-center">
                    <p class="text-sm font-medium text-slate-500">
                        Room is currently unavailable
                    </p>
                </div>
            `
            }
`;

            roomsContainer.appendChild(card);
        });

    } catch (error) {
        const roomsContainer = document.getElementById("rooms-container");

        roomsContainer.textContent = "Failed to load rooms. Please try again later.";

        console.error(error);
    }
}

loadRooms();

