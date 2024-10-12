FROM node:16.20.2 AS build

COPY . /build
WORKDIR /build

RUN npm install
RUN npm job build

FROM nginx:1.26.2

COPY --from=build /build/dist /app
COPY nginx.conf /default.conf

CMD ["/bin/sh", "-c", "DOLLAR=$ envsubst < /default.conf > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]
