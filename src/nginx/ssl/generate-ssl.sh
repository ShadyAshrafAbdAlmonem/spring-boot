#!/bin/bash
# Generate self-signed SSL certificates for development

echo "Generating self-signed SSL certificates for Nginx..."

# Create directory if it doesn't exist
mkdir -p ssl

# Generate private key
openssl genrsa -out ssl/key.pem 2048

# Generate certificate signing request
openssl req -new -key ssl/key.pem -out ssl/cert.csr -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"

# Generate self-signed certificate
openssl x509 -req -days 365 -in ssl/cert.csr -signkey ssl/key.pem -out ssl/cert.pem

# Remove CSR file
rm ssl/cert.csr

echo "SSL certificates generated successfully!"
echo "Certificate: ssl/cert.pem"
echo "Private Key: ssl/key.pem"