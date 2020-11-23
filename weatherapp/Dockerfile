FROM node:12.4.0-alpine as build

WORKDIR /usr/src/app

COPY package.json package-lock.json ./
RUN npm install

COPY . .

RUN npm run build --prod

FROM nginx:1.17.0-alpine

COPY --from=build /usr/src/app/dist/weatherapp /usr/share/nginx/html

EXPOSE 80
