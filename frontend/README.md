## Install the dependencies

The development environment (all the Vue tools, the Vite bundler, etc.) runs with NodeJS. You need
to install all nodeJS dependencies on the first time with:

```
npm install
```

If you don't install new dependencies, you won't need to run it again. Dependencies are installed in
the `node_modules` directory. If you delete this directory, you can always run again the install command.

```

## Start the development server

```

npm run dev

```

Normally you will only need this for the project. The server should auto reload your app each time you make
a change. However, sometimes after a compile error, it fails to detect new changes, you may need to restart it.

## Build the application for deployment

```

npm run build

```

```
