-- Migrate 'spa-client' for tenant1
INSERT INTO registered_client (
    id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
    client_name, client_authentication_methods, authorization_grant_types,
    redirect_uris, scopes, client_settings, token_settings, tenant_id
)
VALUES (
           UUID(), -- change to a generated UUID
           'spa-client',
           NOW(),
           NULL,
           NULL,
           'SPA Client',
           'none',
           'authorization_code,refresh_token',
           'http://localhost:3000/callback',
           'openid,profile',
           '{"requireProofKey":true,"requireAuthorizationConsent":true}',
           '{"accessTokenTimeToLive":"PT30M"}',
           'tenant1'
       )
    ON DUPLICATE KEY UPDATE
                         client_secret = VALUES(client_secret),
                         redirect_uris = VALUES(redirect_uris),
                         scopes = VALUES(scopes),
                         client_settings = VALUES(client_settings),
                         token_settings = VALUES(token_settings);

-- Migrate 'spa-client' for tenant2
INSERT INTO registered_client (
    id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
    client_name, client_authentication_methods, authorization_grant_types,
    redirect_uris, scopes, client_settings, token_settings, tenant_id
)
VALUES (
           UUID(), -- change to a generated UUID
           'spa-client',
           NOW(),
           NULL,
           NULL,
           'SPA Client',
           'none',
           'authorization_code,refresh_token',
           'http://localhost:3000/callback',
           'openid,profile',
           '{"requireProofKey":true,"requireAuthorizationConsent":true}',
           '{"accessTokenTimeToLive":"PT30M"}',
           'tenant2'
       )
    ON DUPLICATE KEY UPDATE
                         client_secret = VALUES(client_secret),
                         redirect_uris = VALUES(redirect_uris),
                         scopes = VALUES(scopes),
                         client_settings = VALUES(client_settings),
                         token_settings = VALUES(token_settings);
