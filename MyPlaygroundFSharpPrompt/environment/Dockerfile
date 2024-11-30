FROM mcr.microsoft.com/dotnet/sdk:9.0 AS builder
WORKDIR /source

COPY ./.config ./.config
COPY ./PromptApi ./PromptApi
COPY ./paket.dependencies .
COPY ./paket.lock .

RUN dotnet tool restore

WORKDIR /source/PromptApi

RUN dotnet paket restore
RUN dotnet publish

FROM mcr.microsoft.com/dotnet/aspnet:9.0
WORKDIR /app

COPY --from=builder /source/PromptApi/bin/Release/net9.0 .

CMD [ "dotnet", "PromptApi.dll" ]