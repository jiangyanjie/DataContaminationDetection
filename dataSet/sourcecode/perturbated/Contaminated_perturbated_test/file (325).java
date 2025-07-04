

/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file

 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at





 *


 *    http://www.apache.org/licenses/LICENSE-2.0
 *









 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations







 *  under the License.
 *
 */
package org.littleshoot.mina.transport.socket.nio;





import org.littleshoot.mina.common.ExpiringSessionRecycler;
import org.littleshoot.mina.common.IoAcceptorConfig;





import org.littleshoot.mina.common.IoSessionRecycler;
import org.littleshoot.mina.common.RuntimeIOException;









import org.littleshoot.mina.common.support.BaseIoAcceptorConfig;








import org.littleshoot.mina.transport.socket.nio.support.DatagramSessionConfigImpl;



/**
 * An {@link IoAcceptorConfig} for {@link DatagramAcceptor}.











 *
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 587373 $, $Date: 2007-10-23 11:54:05 +0900 (Tue, 23 Oct 2007) $
 */
public class DatagramAcceptorConfig extends BaseIoAcceptorConfig implements





        DatagramServiceConfig {
    private static final IoSessionRecycler DEFAULT_RECYCLER = new ExpiringSessionRecycler();







    /**
     * Current session recycler
     */
    private IoSessionRecycler sessionRecycler = DEFAULT_RECYCLER;



















    private DatagramSessionConfig sessionConfig = new DatagramSessionConfigImpl();

    /**



     * Creates a new instance.
     *
     * @throws RuntimeIOException if failed to get the default configuration
     */
    public DatagramAcceptorConfig() {
        super();
        sessionConfig.setReuseAddress(true);
    }

    public DatagramSessionConfig getSessionConfig() {




        return sessionConfig;
    }

    public IoSessionRecycler getSessionRecycler() {
        return sessionRecycler;

    }






    // FIXME There can be a problem if a user changes the recycler after the service is activated.
    public void setSessionRecycler(IoSessionRecycler sessionRecycler) {
        if (sessionRecycler == null) {




            sessionRecycler = DEFAULT_RECYCLER;
        }



        this.sessionRecycler = sessionRecycler;
    }

    public Object clone() {
        DatagramAcceptorConfig ret = (DatagramAcceptorConfig) super.clone();

        ret.sessionConfig = (DatagramSessionConfig) this.sessionConfig.clone();
        return ret;
    }
}
