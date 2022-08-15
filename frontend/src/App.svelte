<script>
    export let apiHost;

    let data = {
        location: "",
        kind: "",
        description: ""
    };

    let addEvent = async () => {
        // const eventToCreate = {
        //     location: data.location,
        //     kind: data.kind,
        //     description: data.description
        // };

        const res = await fetch(`${apiHost}/kinds`, {
            method: 'POST',
            body: JSON.stringify({
                name: data.kind
            }),
            headers: new Headers({'content-type': 'application/json'})
        })

        console.log(res.json());

        data = {
            location: "",
            kind: "",
            description: ""
        };
    };
</script>

<section>
    <div class="container">
        <div class="row mt-5 ">
            <div class="col-md-6">
                <div class="card p-2 shadow">
                    <div class="card-body">
                        <h5 class="card-title mb-4">Create Event</h5>
                        <form>
                            <div class="form-group">
                                <label for="locations">Location</label>
                                <select
                                        class="form-control"
                                        id="locations"
                                        bind:value={data.location}>
                                    <option value="Munich">Munich</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="kinds">Kind</label>
                                <select
                                        class="form-control"
                                        id="kinds"
                                        bind:value={data.kind}>
                                    <option value="Entertainment">Entertainment</option>
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
