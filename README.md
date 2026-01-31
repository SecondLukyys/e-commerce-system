To enable HTTPS solution with nginx was used.

Internet (HTTPS 443)
        ↓
     Nginx
        ↓
Spring Boot (HTTP 8080)
        ↓
      Neo4j

To enable it such commands were used:

Step 0 (prerequisites)
lsb_release -a
nginx -v

Step 1 (Must be true)
ping lukasportfoliosite.com 
should return ip address (46.202.189.28)

Step 2 (install nginx)
sudo apt update
sudo apt install nginx -y
sudo systemctl enable nginx
sudo systemctl start nginx
curl http://localhost

Step 3 (Open firewall for web traffic)
sudo ufw allow 'Nginx Full'
sudo ufw reload

Step 4 (Create Nginx config for your domain)
sudo nano /etc/nginx/sites-available/lukasportfoliosite.com
Paste exact this content:

        server {
        
            listen 80;
            server_name lukasportfoliosite.com www.lukasportfoliosite.com;
        
            location / {
                proxy_pass http://localhost:8080;
                proxy_http_version 1.1;
        
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
            }
        }

Step 5 (Enable the site)
sudo ln -s /etc/nginx/sites-available/lukasportfoliosite.com /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx

Step 6 (Test HTTP via domain IMPORTANT)
If it works then proceed

Step 7 (Install Let’s Encrypt (Certbot))
sudo apt install certbot python3-certbot-nginx -y

Step 8 (Enable HTTPS automatic Nginx SSL)
sudo certbot --nginx -d lukasportfoliosite.com -d www.lukasportfoliosite.com

Steo 9 (Final test)
open www.lukasportfoliosite.com in web browser.










