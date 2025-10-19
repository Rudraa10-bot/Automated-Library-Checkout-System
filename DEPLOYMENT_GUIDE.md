# Deployment Guide

This guide will help you deploy each component of the Library Management System to production.

## Overview

- **Frontend**: Deploy to Vercel
- **Backend**: Deploy to Railway or Render
- **Database**: Use PlanetScale, Railway, or Supabase

## Step 1: Database Setup

### Option A: PlanetScale (Recommended)
1. Go to [PlanetScale](https://planetscale.com)
2. Create a new database
3. Get your connection string
4. Run the database scripts:
   ```bash
   # Connect to your PlanetScale database
   mysql -h your-host -u your-username -p your-database < database/setup.sql
   mysql -h your-host -u your-username -p your-database < database/add_my_books.sql
   ```

### Option B: Railway
1. Go to [Railway](https://railway.app)
2. Create a new MySQL service
3. Get connection details
4. Run database scripts

## Step 2: Backend Deployment

### Using Railway
1. Install Railway CLI: `npm install -g @railway/cli`
2. Login: `railway login`
3. Navigate to backend folder: `cd backend`
4. Initialize: `railway init`
5. Set environment variables:
   ```bash
   railway variables set DATABASE_URL="mysql://username:password@host:port/database"
   railway variables set JWT_SECRET="your-super-secure-jwt-secret"
   railway variables set CORS_ALLOWED_ORIGINS="https://your-frontend.vercel.app"
   ```
6. Deploy: `railway up`

### Using Render
1. Go to [Render](https://render.com)
2. Connect your GitHub repository
3. Select the backend folder
4. Set environment variables in dashboard
5. Deploy

## Step 3: Frontend Deployment

### Using Vercel
1. Go to [Vercel](https://vercel.com)
2. Import your GitHub repository
3. Set root directory to `frontend`
4. Set environment variable:
   - `REACT_APP_API_BASE`: Your deployed backend URL (e.g., `https://your-backend.railway.app/api`)
5. Deploy

## Step 4: Configuration

### Backend Environment Variables
```env
DATABASE_URL=mysql://username:password@host:port/database_name
JWT_SECRET=your-super-secure-jwt-secret-key-at-least-256-bits
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production
```

### Frontend Environment Variables
```env
REACT_APP_API_BASE=https://your-backend.railway.app/api
```

## Step 5: Testing

1. **Test Database Connection**: Verify your backend can connect to the database
2. **Test API Endpoints**: Use Postman or curl to test your backend APIs
3. **Test Frontend**: Verify the frontend can communicate with the backend
4. **Test Full Flow**: Login, issue a book, return a book

## Troubleshooting

### Common Issues

1. **CORS Errors**: Make sure `CORS_ALLOWED_ORIGINS` includes your frontend URL
2. **Database Connection**: Verify your `DATABASE_URL` is correct
3. **JWT Errors**: Ensure `JWT_SECRET` is set and consistent
4. **Build Failures**: Check that all dependencies are in package.json

### Logs and Debugging

- **Railway**: Check logs in Railway dashboard
- **Vercel**: Check function logs in Vercel dashboard
- **Backend**: Add logging to your Spring Boot application

## Security Considerations

1. **Use HTTPS**: All production URLs should use HTTPS
2. **Secure JWT Secret**: Use a strong, random JWT secret
3. **Database Security**: Use strong passwords and restrict access
4. **CORS**: Only allow your frontend domain in CORS settings

## Monitoring

1. **Uptime**: Monitor your services for downtime
2. **Performance**: Monitor response times
3. **Errors**: Set up error tracking (Sentry, etc.)
4. **Database**: Monitor database performance and usage

## Scaling

1. **Database**: Consider read replicas for high traffic
2. **Backend**: Use multiple instances behind a load balancer
3. **CDN**: Use Vercel's global CDN for frontend assets
4. **Caching**: Implement caching for frequently accessed data

## Backup Strategy

1. **Database**: Regular automated backups
2. **Code**: Version control with Git
3. **Environment Variables**: Document all environment variables
4. **Configuration**: Keep configuration files in version control
