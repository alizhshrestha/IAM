# Reference Documentation
### Path 
`C:\Windows\System32\drivers\etc\hosts`
```
127.0.0.1 tenant1.auth.example.com
127.0.0.1 tenant2.auth.example.com
127.0.0.1 localhost.auth.example.com # Optional, if you want a default tenant on localhost
```

### Initial DB Insert
```
use auth_server_db;
insert into tenants (id, domain, name) values ('tenant1', 'tenant1.auth.example.com', 'tenant1');
```

### TEST RUN
```
http://tenant1.auth.example.com:9000/test-tenant
http://tenant2.auth.example.com:9000/test-tenant
http://localhost.auth.example.com:9000/test-tenant
```
