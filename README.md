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
command lsb_release -a
command nginx -v

Step 1 (Must be true)
command ping lukasportfoliosite.com 
should return ip address (46.202.189.28)

Step 2 (install nginx)
command sudo apt update
command sudo apt install nginx -y
command sudo systemctl enable nginx
command sudo systemctl start nginx
command curl http://localhost

Step 3 (Open firewall for web traffic)
command sudo ufw allow 'Nginx Full'
command sudo ufw reload

Step 4 (Create Nginx config for your domain)
command sudo nano /etc/nginx/sites-available/lukasportfoliosite.com
command Paste exact this content:

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
command sudo ln -s /etc/nginx/sites-available/lukasportfoliosite.com /etc/nginx/sites-enabled/
command sudo nginx -t
command sudo systemctl reload nginx

Step 6 (Test HTTP via domain IMPORTANT)
If it works then proceed

Step 7 (Install Let’s Encrypt (Certbot))
command sudo apt install certbot python3-certbot-nginx -y

Step 8 (Enable HTTPS automatic Nginx SSL)
command sudo certbot --nginx -d lukasportfoliosite.com -d www.lukasportfoliosite.com

Steo 9 (Final test)
command open www.lukasportfoliosite.com in web browser.


To start springboot and neo4j and to leave them working when terminal was close these commands were used:
command nohup neo4j console & or command sudo systemctl start neo4j
command nohup mvn spring-boot:run > /dev/null 2>&1 &







