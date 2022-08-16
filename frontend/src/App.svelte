<script>
    export let apiHost;

    let requestingResource = false;

    let kinds = [];
    let locations = [];
    let events = [];
    let data = {};

    fetch(`${apiHost}/kinds`)
        .then(res => res.json())
        .then(fetchedKinds => kinds = fetchedKinds)
        .then(() => {
            fetch(`${apiHost}/locations`)
                .then(res => res.json())
                .then(fetchedLocations => locations = fetchedLocations)
                .then(() => data = createDefaultData())
                .then(() => {
                    fetch(`${apiHost}/events`)
                        .then(res => res.json())
                        .then(fetchedEvents => events = fetchedEvents);
                })
        })

    async function addEvent() {
        const config = {
            method: 'POST',
            body: JSON.stringify(data),
            headers: new Headers({'content-type': 'application/json'})
        };

        requestingResource = true;
        fetch(`${apiHost}/events`, config)
            .then(res => res.json)
            .then(createdEvent => {
                events.push(createdEvent);
                requestingResource = false;
                data = createDefaultData();
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
</script>

<nav class="navbar fixed-top navbar-light bg-primary">
    <a class="navbar-brand text-white" href="#">Event Metastore</a>
</nav>
<section>
    <div class="container">
        <div class="row mt-5 ">
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
                                    on:click|preventDefault={addEvent}
                                    class="btn btn-primary">
                                Create Event
                            </button>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
