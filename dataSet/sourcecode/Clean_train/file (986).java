/*
 * MIT License
 *
 * Copyright (c) 2023 OrdinaryRoad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.live.chat.client.codec.huya.msg.dto;

import com.qq.tars.protocol.tars.TarsInputStream;
import com.qq.tars.protocol.tars.TarsOutputStream;
import com.qq.tars.protocol.tars.TarsStructBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mjz
 * @date 2023/10/10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomBadgeDynamicExternal extends TarsStructBase {

    private String sFloorExter = "";
    private int iFansIdentity;
    private int iBadgeSize;

    @Override
    public void writeTo(TarsOutputStream os) {
        os.write(this.sFloorExter, 0);
        os.write(this.iFansIdentity, 1);
        os.write(this.iFansIdentity, 2);
    }

    @Override
    public void readFrom(TarsInputStream is) {
        this.sFloorExter = is.read(this.sFloorExter, 0, false);
        this.iFansIdentity = is.read(this.iFansIdentity, 1, false);
        this.iBadgeSize = is.read(this.iBadgeSize, 2, false);
    }

    @Override
    public TarsStructBase newInit() {
        return this;
    }
}
