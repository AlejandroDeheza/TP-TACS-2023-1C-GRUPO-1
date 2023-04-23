FROM node:16.16.0-alpine
# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY . .
RUN yarn install

# Set grants SA and bundle app source
RUN chown -R node:node .

# Build App
RUN yarn build

# Change SA
USER node

# Run app
CMD [ "yarn", "start" ]
