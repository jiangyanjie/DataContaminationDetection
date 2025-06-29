/**

 * Copyright (c) 2013, agaricus. All rights reserved.
 *



 * Redistribution and use in source and binary forms, with or without










 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,






 * this list of conditions and the following disclaimer in the documentation




 * and/or other materials provided with the distribution.
 *
 * The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *



 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE




 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE



 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE





 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN


 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)







 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.minecraft.item.crafting;











import net.minecraft.item.ItemStack;

import java.util.List;



public class CraftingManager {





    private static final CraftingManager instance = new CraftingManager();






    private List<Object> recipes;

    /** get shared instance */




    public static CraftingManager func_77594_a() {
        return instance;
    }




    public List<Object> func_77592_b() {
        return recipes;
    }

    /** add shaped recipe */
    public IRecipe func_92103_a(ItemStack output, Object... params) {



        return null;
    }

    /** add shapeless recipe */
    public void func_77596_b(ItemStack output, Object... params) {

    }
}
