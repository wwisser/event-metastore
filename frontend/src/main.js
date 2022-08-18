import App from './App.svelte';

const app = new App({
	target: document.body,
	props: {
		API_HOST: 'http://localhost:8081',
		API_TOKEN: 'dev-admin-token'
	}
});

export default app;