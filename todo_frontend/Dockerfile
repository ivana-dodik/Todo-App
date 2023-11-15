# Use an official Node.js runtime as a parent image
FROM node:18-alpine

# Set the working directory to /app
WORKDIR /app

# Copy package.json and package-lock.json to /app
COPY package*.json ./

# Install dependencies
RUN npm install --production

# Copy the rest of the app to /app
COPY . .

# Expose port 3000 for the React app to run on
EXPOSE 3000

# Start the React app
CMD ["npm", "start"]
