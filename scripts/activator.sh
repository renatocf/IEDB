#!/bin/sh
{
    ACTIVATOR_VERSION=1.2.10
    ACTIVATOR_PACKAGE=activator-$ACTIVATOR_VERSION-minimal
    ACTIVATOR_ZIP=typesafe-$ACTIVATOR_PACKAGE.zip
    ACTIVATOR_CLIENT_URL="http://downloads.typesafe.com/typesafe-activator/$ACTIVATOR_VERSION/$ACTIVATOR_ZIP"
    ACTIVATOR_DIR=$HOME/bin/activator

    # download and extract the client tarball
    rm -rf $ACTIVATOR_DIR
  
    if [ -z "$(which wget)" ]; then
      curl $ACTIVATOR_CLIENT_URL && unzip $ACTIVATOR_ZIP
    else
      wget $ACTIVATOR_CLIENT_URL && unzip $ACTIVATOR_ZIP
    fi
    
    mv $ACTIVATOR_PACKAGE $ACTIVATOR_DIR
    rm -f $ACTIVATOR_ZIP
  
    # remind the user to add to $PATH
    if [ ":$PATH:" != *":$ACTIVATOR_DIR:"* ]; then
        echo "Add the Activator CLI to your PATH (~/.bashrc)"
        echo "PATH=$ACTIVATOR_DIR:\$PATH" >> ~/.bashrc
    fi

    echo "Installation complete"
}
