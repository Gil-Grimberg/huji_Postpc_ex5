since there was no request regarding TODO items texts that needed to be disabled, every TODO item on
the list can be edited simply by clicking on the text. in order to let users edit a description of 
an existing TODO item i would define the following UX flow:

1. disable every TODO item's text.
2. create an "editing" button next to the "delete" button.the editing button can be visualized as a pen.
3. when the new button is clicked, "delete" button is gone, 2 new buttons appear (V mark and X mark) 
   and the TODO item's text is enabled for editing.
4. when clicking on "V mark" new description is saved, both "V mark" and "X mark" disappear, "delete" and "edit" shows up again.
5. when clicking on "X mark" ignore new description and display the old description (the one that was saved before user started editing).

this flow is very intuitive and consistent with regular "edit" flows in the android world, and is very easy to implement. 
