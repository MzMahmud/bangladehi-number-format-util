from constants import constant_by_language, number_to_word
from bd_number_formatter import get_formatted_number, get_lakh_crore_split
from char_processors import find_out_language, convert_index


def convert_to_word(num_str: str) -> str:
    language = find_out_language(num_str)

    in_word = ''
    starts_with_minus = num_str.startswith('-')
    starts_with_plus = num_str.startswith('+')
    if starts_with_plus or starts_with_minus:
        num_str = num_str[1:]
        in_word = constant_by_language[language + "-"] + ' ' if starts_with_minus else ''

    decimal_split = num_str.split('.')

    in_word += convert_to_word_integer(decimal_split[0], language)

    if len(decimal_split) > 1:
        after_decimal_in_word = convert_to_word_after_decimal_point(decimal_split[1], language)
        in_word += f" {constant_by_language[language + '.']} {after_decimal_in_word}"

    return in_word


def convert_to_word_integer(num_str: str, language: str) -> str:
    if not num_str:
        return ''

    if len(num_str) <= 7:
        return convert_to_word_below_crore(get_formatted_number(num_str), language)

    lakh, crore = get_lakh_crore_split(num_str)

    above_crore = convert_to_word_integer(crore, language) + " " + constant_by_language[language + "Crore"]
    return above_crore + " " + convert_to_word_below_crore(get_formatted_number(lakh), language)


def convert_to_word_after_decimal_point(num_str: str, language: str) -> str:
    if not num_str:
        return ''
    return ' '.join(map(lambda digit: convert_to_word_below_hundred(digit, language), num_str))


def convert_to_word_below_crore(bangladeshi_comma_formatted_number: str, language: str) -> str:
    split_by_comma = list(reversed(bangladeshi_comma_formatted_number.split(",")))

    number_in_word = []
    for comma_split_index in range(len(split_by_comma) - 1, 0, -1):
        smaller_num_in_word = convert_to_word_below_hundred(split_by_comma[comma_split_index], language)
        index_char = convert_index(comma_split_index, language)
        if smaller_num_in_word:
            number_in_word.append(smaller_num_in_word)
            number_in_word.append(" ")
            number_in_word.append(number_to_word["comma-split-" + index_char])
            number_in_word.append(" ")

    number_in_word.append(convert_to_word_below_thousand(split_by_comma[0], language))
    return ''.join(number_in_word)


def convert_to_word_below_thousand(num_str: str, language: str) -> str:
    if len(num_str) < 3:
        return convert_to_word_below_hundred(num_str, language)

    hundredth_digit = num_str[0]
    below_hundred = num_str[1:]

    const0 = constant_by_language[language + "0"]
    if const0 == hundredth_digit:
        return convert_to_word_below_hundred(below_hundred, language)

    const100 = constant_by_language[language + "100"]
    in_word = number_to_word[hundredth_digit] + number_to_word[const100]

    below_hundred_in_word = convert_to_word_below_hundred(below_hundred, language)
    if below_hundred_in_word != '':
        in_word += (" " + below_hundred_in_word)

    return in_word


def convert_to_word_below_hundred(num_str: str, language: str) -> str:
    if num_str in number_to_word:
        return number_to_word[num_str]

    const00 = constant_by_language[language + "00"]
    if const00 == num_str:
        return ""

    tenths_digit = num_str[0]
    units_digit = num_str[1]

    const0 = constant_by_language[language + "0"]
    if const0 == tenths_digit:
        return number_to_word[units_digit]

    return number_to_word[tenths_digit + const0] + " " + number_to_word[units_digit]
