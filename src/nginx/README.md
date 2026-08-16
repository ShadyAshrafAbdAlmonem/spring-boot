# Nginx Setup for Inventory Management System

## Overview
Nginx is configured as a reverse proxy with:
- **HTTP** (port 80): Redirects to HTTPS
- **HTTPS** (port 443): Main application access with SSL
- **Rate limiting**: Protection against brute force attacks
- **Security headers**: X-Frame-Options, X-Content-Type-Options, etc.
- **Actuator restrictions**: Health check is public, other actuator endpoints restricted to localhost/private networks

## Directory Structure
```
src/nginx/
├── nginx.conf          # Main Nginx configuration
├── ssl/
│   ├── cert.pem       # SSL certificate (self-signed for development)
│   ├── key.pem        # SSL private key
│   └── generate-ssl.sh # SSL certificate generation script
└── README.md          # This file
```

## Quick Start

### 1. Generate SSL Certificates
**On Linux/Mac:**
```bash
cd src/nginx/ssl
bash generate-ssl.sh
```

**On Windows (PowerShell):**
```powershell
cd src/nginx/ssl
openssl genrsa -out key.pem 2048
openssl req -new -key key.pem -out cert.csr -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"
openssl x509 -req -days 365 -in cert.csr -signkey key.pem -out cert.pem
Remove-Item cert.csr
```

### 2. Start the Application
```bash
# From project root
docker-compose up -d
```

### 3. Access the Application
- **HTTPS**: https://localhost (accept self-signed certificate warning)
- **Health Check**: https://localhost/actuator/health
- **Swagger UI**: https://localhost/swagger-ui/index.html

## For Production

### Replace Self-Signed Certificates
1. Obtain SSL certificates from a trusted CA (Let's Encrypt, DigiCert, etc.)
2. Replace `src/nginx/ssl/cert.pem` and `src/nginx/ssl/key.pem`
3. Update `nginx.conf` to remove the self-signed certificate warning if needed

### Recommended Changes
1. Update `server_name` in nginx.conf to your actual domain
2. Configure Let's Encrypt SSL certificates
3. Adjust rate limiting zones based on your traffic needs
4. Restrict actuator endpoints further (currently allows 172.16.x.x, 192.168.x.x)
5. Add authentication for actuator endpoints
6. Enable HTTP/2 in the HTTPS server block (already enabled)

## Nginx Features

### Rate Limiting
- **API endpoints**: 10 requests/second with burst of 20
- **Login endpoint**: 5 requests/second with burst of 5 (prevents brute force)
- **General traffic**: 10 requests/second with burst of 20

### Security Headers
- `X-Frame-Options: SAMEORIGIN` - Prevents clickjacking
- `X-Content-Type-Options: nosniff` - Prevents MIME type sniffing
- `X-XSS-Protection: 1; mode=block` - Enables XSS filtering
- `Referrer-Policy: strict-origin-when-cross-origin` - Controls referrer information

### Actuator Security
- `/actuator/health`: Publicly accessible
- `/actuator/**`: Restricted to localhost and private networks (192.168.x.x, 172.16.x.x)

## Troubleshooting

### SSL Certificate Errors
If you get SSL certificate warnings, this is normal for self-signed certificates. You can:
1. Accept the warning in your browser
2. Import the certificate into your browser's trusted store
3. Use `curl -k https://localhost` to bypass verification

### Nginx Won't Start
Check Nginx logs:
```bash
docker logs inventory-nginx
```

Common issues:
- SSL certificate files missing or incorrect permissions
- Port 80 or 443 already in use

### Application Not Accessible
1. Verify all containers are running: `docker-compose ps`
2. Check application logs: `docker logs inventory-app`
3. Check Nginx logs: `docker logs inventory-nginx`
4. Ensure ports 80 and 443 are not blocked by firewall

## Architecture
```
Internet → [Nginx:80/443] → [Spring Boot App:8080]
                                    ↓
                              [MySQL:3306]
                              [Redis:6379]
```

Nginx acts as the entry point, handling SSL termination, rate limiting, and security before forwarding requests to the Spring Boot application.
</parameter>
<task_progress>
- [x] Analyze Dockerfile and docker-compose.yml
- [x] Identify network connectivity issues
- [x] Create Maven settings.xml with reliable mirrors
- [x] Update Dockerfile to use custom Maven settings
- [x] Rebuild Docker image to verify the fix
- [x] Fix port conflict with local MySQL
- [x] Start all Docker containers
- [x] Enable Spring Boot Maven plugin for executable JAR
- [x] Rebuild application with correct configuration
- [x] Restart containers with new image
- [x] Verify application is running correctly
- [x] Provide instructions for running the application
- [x] Explain build time concerns and confirm fix
- [x] Create Nginx configuration
- [x] Update docker-compose.yml to include Nginx
- [x] Create SSL certificate generation script
- [x] Create Nginx setup documentation
- [ ] Generate self-signed SSL certificates for development
- [ ] Test Nginx reverse proxy
</task_progress>
</write_to_file>