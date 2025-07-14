export const logout = () => {
  const tenantId = localStorage.getItem("tenant_id");
  const idToken = sessionStorage.getItem("id_token");
  const endSessionEndpoint = `http://${tenantId}.auth.example.com:9000/connect/logout`;
  const redirectUri = encodeURIComponent("http://localhost:3000/login");

  if (!idToken) {
    console.warn("id_token not found. Performing local cleanup only.");
    sessionStorage.clear();
    localStorage.clear();
    window.location.href = "/login";
    return;
  }

  sessionStorage.clear();
  localStorage.clear();

  const logoutUrl = `${endSessionEndpoint}?post_logout_redirect_uri=${redirectUri}&id_token_hint=${idToken}`;
  window.location.href = logoutUrl;
};
