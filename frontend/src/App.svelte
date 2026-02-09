<script>
    import { t } from './i18n.js';
    import { tweened } from 'svelte/motion';
    import { cubicOut } from 'svelte/easing';

    export let API_HOST;
    export let API_TOKEN;
    const HEADERS = new Headers({'content-type': 'application/json', 'Authorization': API_TOKEN});

    let lang = 'de';
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

    const currentYear = new Date().getFullYear();
    const displayYears = [currentYear, currentYear - 1, currentYear - 2];
    const animatedCounts = tweened([0, 0, 0], { duration: 1000, easing: cubicOut });

    $: {
        const newCounts = displayYears.map(year => 
            events.filter(e => new Date(e.dateTime).getFullYear() === year).length
        );
        animatedCounts.set(newCounts);
    }

    $: sorted = [...events].sort((a, b) => new Date(b.dateTime) - new Date(a.dateTime));
    $: totalPages = Math.ceil(sorted.length / perPage);
    $: paged = sorted.slice((currentPage - 1) * perPage, currentPage * perPage);
    $: if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
    $: tr = t[lang];
</script>

<style>
    :global(*) { box-sizing: border-box; margin: 0; padding: 0; }
    :global(body) { margin: 0; font-family: system-ui, -apple-system, sans-serif; background: #09090b; color: #fafafa; min-height: 100vh; }
    :global(html) { background: #09090b; }

    .app { display: grid; grid-template-columns: 300px 1fr; min-height: 100vh; }
    .sidebar { background: #09090b; border-right: 1px solid #27272a; padding: 24px; display: flex; flex-direction: column; gap: 20px; }
    .main { padding: 24px; overflow-x: auto; background: #09090b; display: flex; flex-direction: column; height: 100vh; }
    
    h1 { font-size: 20px; font-weight: 700; color: #fafafa; margin-bottom: 4px; }
    h2 { font-size: 13px; font-weight: 600; color: #71717a; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 0.5px; }

    .form-group { margin-bottom: 14px; }
    label { display: block; font-size: 13px; color: #a1a1aa; margin-bottom: 6px; }
    input, select { width: 100%; padding: 10px 12px; background: #18181b; border: 1px solid #27272a; border-radius: 6px; color: #fafafa; font-size: 14px; }
    input:focus, select:focus { outline: none; border-color: #3b82f6; }
    input[type="datetime-local"]::-webkit-calendar-picker-indicator { filter: invert(1); cursor: pointer; }

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

    .header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
    .lang-switcher { display: flex; gap: 4px; background: #18181b; border: 1px solid #27272a; border-radius: 6px; padding: 2px; }
    .lang-btn { padding: 4px 10px; background: transparent; border: none; color: #71717a; cursor: pointer; border-radius: 4px; font-size: 12px; font-weight: 500; }
    .lang-btn.active { background: #3b82f6; color: #fff; }

    .stats-wrapper { display: flex; flex: 1; align-items: center; justify-content: center; padding: 20px 0; }
    .stats-grid { display: grid; grid-template-columns: repeat(3, 140px); gap: 20px; }
    .stat-card { background: linear-gradient(135deg, #18181b 0%, #27272a 100%); border: 1px solid #27272a; border-radius: 8px; padding: 16px; text-align: center; display: flex; flex-direction: column; align-items: center; justify-content: center; height: 110px; }
    .stat-year { font-size: 12px; color: #71717a; font-weight: 600; margin-bottom: 4px; text-transform: uppercase; letter-spacing: 0.5px; }
    .stat-count { font-size: 32px; font-weight: 700; color: #3b82f6; line-height: 1.1; }
    .stat-label { font-size: 11px; color: #a1a1aa; margin-top: 4px; }
</style>

<div class="app">
    <aside class="sidebar">
        <div class="header-row">
            <div>
                <h1>{tr.title}</h1>
                <span class="badge">{tr.admin}</span>
            </div>
            <div class="lang-switcher">
                <button class="lang-btn" class:active={lang === 'de'} on:click={() => lang = 'de'}>DE</button>
                <button class="lang-btn" class:active={lang === 'en'} on:click={() => lang = 'en'}>EN</button>
            </div>
        </div>

        <div>
            <h2>{tr.createEvent}</h2>
            <form on:submit|preventDefault={createEvent}>
                <div class="form-group">
                    <label>{tr.dateTime}</label>
                    <input type="datetime-local" bind:value={data.dateTime}>
                </div>
                <div class="form-group">
                    <label>{tr.location}</label>
                    <select bind:value={data.locationId}>
                        {#each locations as loc}<option value={loc.id}>{loc.name}</option>{/each}
                    </select>
                </div>
                <div class="form-group">
                    <label>{tr.kind}</label>
                    <select bind:value={data.kindId}>
                        {#each kinds as k}<option value={k.id}>{k.name}</option>{/each}
                    </select>
                </div>
                <div class="form-group">
                    <label>{tr.size}</label>
                    <select bind:value={data.description}>
                        <option>Solo</option><option>Duo</option><option>Trio</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="width:100%">{tr.createBtn}</button>
            </form>
        </div>

        <div class="divider"></div>

        <div>
            <h2>{tr.addLocation}</h2>
            <div class="inline-form">
                <input type="text" bind:value={newLocation} placeholder={tr.namePlaceholder}>
                <button class="btn btn-ghost btn-sm" on:click={addLocation}>{tr.addBtn}</button>
            </div>
        </div>

        <div>
            <h2>{tr.addKind}</h2>
            <div class="inline-form">
                <input type="text" bind:value={newKind} placeholder={tr.namePlaceholder}>
                <button class="btn btn-ghost btn-sm" on:click={addKind}>{tr.addBtn}</button>
            </div>
        </div>
    </aside>

    <main class="main">
        <h2>{tr.events} ({sorted.length})</h2>
        {#if paged.length}
            <table>
                <thead>
                    <tr>
                        <th>{tr.id}</th>
                        <th>{tr.date}</th>
                        <th>{tr.location}</th>
                        <th>{tr.kind}</th>
                        <th>{tr.size}</th>
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
                            <td><button class="btn btn-danger btn-sm" on:click={() => deleteEvent(event.id)}>{tr.delete}</button></td>
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

            <div class="stats-wrapper">
                <div class="stats-grid">
                    {#each displayYears as year, i}
                        <div class="stat-card">
                            <div class="stat-year">{year}</div>
                            <div class="stat-count">{Math.round($animatedCounts[i])}</div>
                            <div class="stat-label">{tr.eventsLabel}</div>
                        </div>
                    {/each}
                </div>
            </div>
        {:else}
            <div class="empty">{tr.noEvents}</div>
        {/if}
    </main>
</div>
