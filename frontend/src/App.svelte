<script>
    export let API_HOST;
    export let API_TOKEN;
    const HEADERS = new Headers({'content-type': 'application/json', 'Authorization': API_TOKEN});

    let kinds = [], locations = [], events = [];
    let data = {};
    let newLocation = '', newKind = '';
    let currentPage = 1;
    const perPage = 8;

    fetchAll();

    async function fetchAll() {
        const [k, l, e] = await Promise.all([
            fetch(`${API_HOST}/kinds`).then(r => r.json()),
            fetch(`${API_HOST}/locations`).then(r => r.json()),
            fetch(`${API_HOST}/events`).then(r => r.json())
        ]);
        kinds = k; locations = l; events = e;
        data = defaultData();
    }

    function defaultData() {
        return { dateTime: new Date().toISOString().slice(0, 16), kindId: kinds[0]?.id, locationId: locations[0]?.id, description: 'Duo' };
    }

    async function createEvent() {
        const res = await fetch(`${API_HOST}/events`, { method: 'POST', body: JSON.stringify(data), headers: HEADERS });
        events = [...events, await res.json()];
        data = defaultData();
    }

    async function deleteEvent(id) {
        await fetch(`${API_HOST}/events/${id}`, { method: 'DELETE', headers: HEADERS });
        events = events.filter(e => e.id !== id);
    }

    async function addLocation() {
        if (!newLocation.trim()) return;
        const res = await fetch(`${API_HOST}/locations`, { method: 'POST', body: JSON.stringify({ name: newLocation }), headers: HEADERS });
        locations = [...locations, await res.json()];
        newLocation = '';
    }

    async function addKind() {
        if (!newKind.trim()) return;
        const res = await fetch(`${API_HOST}/kinds`, { method: 'POST', body: JSON.stringify({ name: newKind }), headers: HEADERS });
        kinds = [...kinds, await res.json()];
        newKind = '';
    }

    function getName(arr, id) { return arr.find(x => x.id === id)?.name || '—'; }
    function formatDate(dt) { return new Date(dt).toLocaleDateString('de-DE', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' }); }

    $: sorted = [...events].sort((a, b) => new Date(b.dateTime) - new Date(a.dateTime));
    $: totalPages = Math.ceil(sorted.length / perPage);
    $: paged = sorted.slice((currentPage - 1) * perPage, currentPage * perPage);
    $: if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
</script>

<style>
    :global(*) { box-sizing: border-box; margin: 0; padding: 0; }
    :global(body) { margin: 0; font-family: system-ui, -apple-system, sans-serif; background: #09090b; color: #fafafa; min-height: 100vh; }
    :global(html) { background: #09090b; }

    .app { display: grid; grid-template-columns: 300px 1fr; min-height: 100vh; }
    .sidebar { background: #09090b; border-right: 1px solid #27272a; padding: 24px; display: flex; flex-direction: column; gap: 20px; }
    .main { padding: 24px; overflow-x: auto; background: #09090b; }

    h1 { font-size: 20px; font-weight: 700; color: #fafafa; margin-bottom: 4px; }
    h2 { font-size: 13px; font-weight: 600; color: #71717a; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 0.5px; }

    .form-group { margin-bottom: 14px; }
    label { display: block; font-size: 13px; color: #a1a1aa; margin-bottom: 6px; }
    input, select { width: 100%; padding: 10px 12px; background: #18181b; border: 1px solid #27272a; border-radius: 6px; color: #fafafa; font-size: 14px; }
    input:focus, select:focus { outline: none; border-color: #3b82f6; }

    .btn { padding: 10px 16px; border: none; border-radius: 6px; font-size: 14px; font-weight: 500; cursor: pointer; transition: all 0.15s; }
    .btn-primary { background: #3b82f6; color: #fff; }
    .btn-primary:hover { background: #2563eb; }
    .btn-ghost { background: #18181b; color: #a1a1aa; border: 1px solid #27272a; }
    .btn-ghost:hover { background: #27272a; color: #fafafa; }
    .btn-danger { background: #7f1d1d; color: #fca5a5; }
    .btn-danger:hover { background: #991b1b; }
    .btn-sm { padding: 6px 12px; font-size: 13px; }

    .divider { height: 1px; background: #27272a; margin: 4px 0 16px 0; }

    .inline-form { display: flex; gap: 8px; }
    .inline-form input { flex: 1; }

    table { width: 100%; border-collapse: collapse; background: #18181b; border-radius: 8px; overflow: hidden; }
    th, td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #27272a; }
    th { background: #09090b; font-size: 12px; font-weight: 600; color: #71717a; text-transform: uppercase; letter-spacing: 0.5px; }
    td { font-size: 14px; color: #e4e4e7; }
    tr:hover td { background: #27272a; }
    tr:last-child td { border-bottom: none; }

    .pagination { display: flex; gap: 6px; justify-content: center; margin-top: 20px; }
    .page-btn { min-width: 32px; height: 32px; display: flex; align-items: center; justify-content: center; background: #18181b; border: 1px solid #27272a; border-radius: 6px; color: #71717a; cursor: pointer; font-size: 13px; }
    .page-btn:hover:not(:disabled):not(.active) { background: #27272a; color: #fafafa; }
    .page-btn.active { background: #3b82f6; color: #fff; border-color: #3b82f6; }
    .page-btn:disabled { opacity: 0.3; cursor: not-allowed; }

    .empty { text-align: center; padding: 48px; color: #52525b; }
    .badge { display: inline-block; padding: 4px 10px; background: #18181b; border: 1px solid #27272a; border-radius: 4px; font-size: 12px; color: #71717a; }
</style>

<div class="app">
    <aside class="sidebar">
        <div>
            <h1>Event Metastore</h1>
            <span class="badge">Admin</span>
        </div>

        <div>
            <h2>Create Event</h2>
            <form on:submit|preventDefault={createEvent}>
                <div class="form-group">
                    <label>Date & Time</label>
                    <input type="datetime-local" bind:value={data.dateTime}>
                </div>
                <div class="form-group">
                    <label>Location</label>
                    <select bind:value={data.locationId}>
                        {#each locations as loc}<option value={loc.id}>{loc.name}</option>{/each}
                    </select>
                </div>
                <div class="form-group">
                    <label>Kind</label>
                    <select bind:value={data.kindId}>
                        {#each kinds as k}<option value={k.id}>{k.name}</option>{/each}
                    </select>
                </div>
                <div class="form-group">
                    <label>Size</label>
                    <select bind:value={data.description}>
                        <option>Solo</option><option>Duo</option><option>Trio</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="width:100%">Create Event</button>
            </form>
        </div>

        <div class="divider"></div>

        <div>
            <h2>Add Location</h2>
            <div class="inline-form">
                <input type="text" bind:value={newLocation} placeholder="Name...">
                <button class="btn btn-ghost btn-sm" on:click={addLocation}>Add</button>
            </div>
        </div>

        <div>
            <h2>Add Kind</h2>
            <div class="inline-form">
                <input type="text" bind:value={newKind} placeholder="Name...">
                <button class="btn btn-ghost btn-sm" on:click={addKind}>Add</button>
            </div>
        </div>
    </aside>

    <main class="main">
        <h2>Events ({sorted.length})</h2>
        {#if paged.length}
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Location</th>
                        <th>Kind</th>
                        <th>Size</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {#each paged as event (event.id)}
                        <tr>
                            <td>#{event.id}</td>
                            <td>{formatDate(event.dateTime)}</td>
                            <td>{getName(locations, event.locationId)}</td>
                            <td>{getName(kinds, event.kindId)}</td>
                            <td>{event.description}</td>
                            <td><button class="btn btn-danger btn-sm" on:click={() => deleteEvent(event.id)}>Delete</button></td>
                        </tr>
                    {/each}
                </tbody>
            </table>

            {#if totalPages > 1}
                <div class="pagination">
                    <button class="page-btn" on:click={() => currentPage--} disabled={currentPage === 1}>←</button>
                    {#each Array(totalPages) as _, i}
                        <button class="page-btn" class:active={currentPage === i + 1} on:click={() => currentPage = i + 1}>{i + 1}</button>
                    {/each}
                    <button class="page-btn" on:click={() => currentPage++} disabled={currentPage === totalPages}>→</button>
                </div>
            {/if}
        {:else}
            <div class="empty">No events yet</div>
        {/if}
    </main>
</div>
