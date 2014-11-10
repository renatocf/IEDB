#!/bin/sh
{
    HEROKU_CLIENT_URL="https://s3.amazonaws.com/assets.heroku.com/heroku-client/heroku-client.tgz"
    HEROKU_DIR=$HOME/bin/heroku

    # download and extract the client tarball
    rm -rf $HEROKU_DIR
    mkdir -p $HEROKU_DIR
    cd $HEROKU_DIR
  
    if [ -z "$(which wget)" ]; then
      curl -s $HEROKU_CLIENT_URL | tar xz
    else
      wget -qO- $HEROKU_CLIENT_URL | tar xz
    fi
  
    mv heroku-client/* .
    rmdir heroku-client

    # remind the user to add to $PATH
    if [ ":$PATH:" != *":$HEROKU_DIR/bin:"* ]; then
	echo "Add the Heroku CLI to your PATH using:"
	echo "$ echo 'PATH=\"$HEROKU_DIR/bin:\$PATH\"' >> ~/.bashrc"
    fi

    echo "Installation complete"
}
