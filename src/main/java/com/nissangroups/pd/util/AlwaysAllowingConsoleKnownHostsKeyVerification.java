package com.nissangroups.pd.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000059.util.B000059MoveFilesTasklet;
import com.sshtools.j2ssh.transport.ConsoleKnownHostsKeyVerification;
import com.sshtools.j2ssh.transport.InvalidHostFileException;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;

public class AlwaysAllowingConsoleKnownHostsKeyVerification extends
        ConsoleKnownHostsKeyVerification {
	
	private static final Log LOG = LogFactory.getLog(B000059MoveFilesTasklet.class
			.getName());
 
    public AlwaysAllowingConsoleKnownHostsKeyVerification()
            throws InvalidHostFileException {
        super();        
    }
 
    public void onHostKeyMismatch(String s, SshPublicKey sshpublickey,
            SshPublicKey sshpublickey1) {
        try
        {            
            allowHost(s, sshpublickey, false);
        }
        catch(Exception exception)
        {
        	LOG.error(exception);
        }
    }
 
    @Override
    public void onUnknownHost(String s, SshPublicKey sshpublickey) {
        try
        {
            
            allowHost(s, sshpublickey, false);
        }
        catch(Exception exception)
        {
        	LOG.error(exception);
        }
    }
 
}