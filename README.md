# Bangladeshi Number Format Util
This is a project to convert any Bangla or English number string into Bangladeshi number format which is the [Crore-Lakh](https://en.wikipedia.org/wiki/Bengali_numerals) system. 

This project gives two APIs.

1. given a `number string`, `getFormattedNumber` would return another string that is thousand and hudred seperated by `comma`.

1. given a `number string`, `convertToWord` returns another string which is the number in word.

## Example
- Integer number 

```
input
406940404
৪০৬৯৪০৪০৪

getFormattedNumber(input)
40,69,40,404
৪০,৬৯,৪০,৪০৪

convertToWord(input)
forty crore sixty nine lakh forty thousand fourhundred four
চল্লিশ কোটি ঊনসত্তর লক্ষ চল্লিশ হাজার চারশত চার
```

```
input
-700067
-৭০০০৬৭

getFormattedNumber(input)
-7,00,067
-৭,০০,০৬৭

convertToWord(input)
negative seven lakh sixty seven
ঋণাত্মক সাত লক্ষ সাতষট্টি
```

- Decimal number 
```
input
3.14159265359
৩.১৪১৫৯২৬৫৩৫৯

getFormattedNumber(input)
3.14159265359
৩.১৪১৫৯২৬৫৩৫৯

convertToWord(input)
three point one four one five nine two six five three five nine
তিন দশমিক এক চার এক পাঁচ নয় দুই ছয় পাঁচ তিন পাঁচ নয়
```


```
input
-147570.0641
-১৪৭৫৭০.০৬৪১

getFormattedNumber(input)
-1,47,570.0641
-১,৪৭,৫৭০.০৬৪১

convertToWord(input)
negative one lakh forty seven thousand fivehundred seventy
point zero six four one
ঋণাত্মক এক লক্ষ সাতচল্লিশ হাজার পাঁচশত সত্তর দশমিক শুন্য ছয় চার এক
```

## Installation
As until now, this project is implemented in `Java` and `Javaspript`. 

- `Java` implementation is written in `JDK 11`. The code would work for `Java 8+`.
- `Javaspript` implementation uses `ES6` syntax and `modules`. For using in browser use `<script type="module" src="main.js"></script>`. In node use `"type": "module"` in `package.json`
