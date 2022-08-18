<script>
    export let API_HOST;
    export let API_TOKEN;
    const ADMIN_HEADER = new Headers({'content-type': 'application/json', 'Authorization': API_TOKEN});

    let requestingResource = false;

    let kinds = [];
    let locations = [];
    let events = [];
    let data = {};

    let locationName;
    let kindName;

    fetchInitialData();

    function fetchInitialData() {
        fetch(`${API_HOST}/kinds`)
            .then(res => res.json())
            .then(fetchedKinds => kinds = fetchedKinds)
            .then(() => {
                fetch(`${API_HOST}/locations`)
                    .then(res => res.json())
                    .then(fetchedLocations => locations = fetchedLocations)
                    .then(() => data = createDefaultData())
                    .then(() => {
                        fetch(`${API_HOST}/events`)
                            .then(res => res.json())
                            .then(fetchedEvents => events = fetchedEvents);
                    })
            })
    }

    async function postEvent() {
        const config = {
            method: 'POST',
            body: JSON.stringify(data),
            headers: ADMIN_HEADER
        };

        requestingResource = true;
        fetch(`${API_HOST}/events`, config)
            .then(res => res.json())
            .then(createdEvent => {
                events = events.concat(createdEvent);
                requestingResource = false;
                data = createDefaultData();
            });
    }


    async function postLocation() {
        const config = {
            method: 'POST',
            body: JSON.stringify({name: locationName}),
            headers: ADMIN_HEADER
        };

        requestingResource = true;
        fetch(`${API_HOST}/locations`, config)
            .then(res => res.json())
            .then(createdLocation => {
                locations = locations.concat(createdLocation);
                requestingResource = false;
            });
    }

    async function postKind() {
        const config = {
            method: 'POST',
            body: JSON.stringify({name: kindName}),
            headers: ADMIN_HEADER
        };

        requestingResource = true;
        fetch(`${API_HOST}/kinds`, config)
            .then(res => res.json())
            .then(createdKind => {
                kinds = kinds.concat(createdKind);
                requestingResource = false;
            });
    }

    async function deleteEvent(eventToDelete) {
        fetch(`${API_HOST}/events/${eventToDelete.id}`, {method: 'DELETE', headers: ADMIN_HEADER})
            .then(() => {
                events = events.filter(event => event !== eventToDelete)
            });
    }

    function createDefaultData() {
        return {
            dateTime: new Date().toISOString().slice(0, 16),
            kindId: kinds[0].id,
            locationId: locations[0].id,
            description: 'Duo'
        }
    }

    function getKindNameById(id) {
        return kinds.find(kind => kind.id === id).name;
    }

    function getLocationNameById(id) {
        return locations.find(location => location.id === id).name;
    }
</script>

<nav class="navbar fixed-top navbar-light bg-primary">
    <a class="navbar-brand text-white" href="#">
        <img style="filter: opacity(0.5) drop-shadow(0 0 0 blue)" src="/icon.png" height="40" alt="logo">
        <span>Event Metastore</span>
    </a>
</nav>
<section>
    <div class="container">
        <div class="row mt-5">
            <div class="col-md-6">
                <div class="card mt-5 p-2 shadow">
                    <div class="card-body">
                        <h5 class="card-title mb-4">Create Event</h5>

                        <form>
                            <div class="form-group">
                                <label for="date">Date</label>
                                <input class="form-control" id="date" bind:value={data.dateTime}
                                       type="datetime-local">
                            </div>
                            <div class="form-group">
                                <label for="locations">Location</label>
                                <select
                                        class="form-control"
                                        id="locations"
                                        bind:value={data.locationId}
                                >
                                    {#each locations as location}
                                        <option value={location.id}>{location.name}</option>
                                    {/each}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="kinds">Kind</label>
                                <select
                                        class="form-control"
                                        id="kinds"
                                        bind:value={data.kindId}>
                                    {#each kinds as kind}
                                        <option value={kind.id}>{kind.name}</option>
                                    {/each}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="appointments">Appointments</label>
                                <select
                                        class="form-control"
                                        id="appointments"
                                        bind:value={data.description}>
                                    <option value="Solo">Solo</option>
                                    <option value="Duo">Duo</option>
                                    <option value="Trio">Trio</option>
                                </select>
                            </div>
                            <button
                                    type="submit"
                                    disabled={requestingResource}
                                    on:click|preventDefault={postEvent}
                                    class="btn btn-primary">
                                Create
                            </button>
                        </form>
                        <hr>
                        <h5 class="card-title">Create Relation</h5>
                        <form>
                            <label for="locationName">Location Name</label>
                            <div class="w-50 form-group input-group">
                                <input class="form-control " id="locationName" bind:value={locationName}
                                       type="text">
                                <div class="input-group-append">
                                    <button
                                            type="submit"
                                            disabled={requestingResource || !locationName}
                                            on:click|preventDefault={postLocation}
                                            class="btn btn-primary">
                                        Create
                                    </button>
                                </div>
                            </div>
                        </form>
                        <form>
                            <label for="kindName">Kind Name</label>
                            <div class="w-50 form-group input-group">
                                <input class="form-control" id="kindName" bind:value={kindName}
                                       type="text">
                                <div class="input-group-append">
                                    <button
                                            type="submit"
                                            disabled={requestingResource || !kindName}
                                            on:click|preventDefault={postKind}
                                            class="btn btn-primary">
                                        Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
            <div class="mt-5 col-md-4">
                {#each events as event}
                    <div class="card mb-3">
                        <div class="card-header">
                            Event #{event.id}
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <p class="m-0 ">
                                    <i class="fas fa-lg fa-calendar-days"></i>
                                    <span class="float-right">
                                        {new Date(event.dateTime).toLocaleDateString()}
                                        {new Date(event.dateTime).toLocaleTimeString()}
                                    </span>
                                </p>
                                <p class="m-0">
                                    <i class="fas fa-location-dot mr-2"></i>
                                    <span class="float-right">
                                    {getLocationNameById(event.locationId)}
                                    </span>
                                </p>
                                <p class="m-0">
                                    <i class="fa-solid fa-champagne-glasses mr-2"></i>
                                    <span class="float-right">
                                    {getKindNameById(event.kindId)}
                                    </span>
                                </p>
                                <p class="m-0">
                                    <i class="fa-solid fa-people-arrows mr-2"></i>
                                    <span class="float-right">
                                    {event.description}
                                    </span>
                                </p>
                            </div>

                            <button class="btn btn-danger" on:click={deleteEvent(event)}>
                                Delete
                            </button>
                        </div>
                    </div>
                {/each}
            </div>
        </div>
    </div>

</section>
